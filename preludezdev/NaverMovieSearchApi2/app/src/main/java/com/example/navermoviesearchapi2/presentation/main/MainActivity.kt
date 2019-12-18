package com.example.navermoviesearchapi2.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navermoviesearchapi2.R
import com.example.navermoviesearchapi2.base.BaseActivity
import com.example.navermoviesearchapi2.databinding.ActivityMainBinding
import com.example.navermoviesearchapi2.presentation.detail.DetailActivity
import com.example.navermoviesearchapi2.utils.plusAssign
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val compositeDisposable = CompositeDisposable()
    private val backButtonSubject: Subject<Long> = BehaviorSubject.createDefault(0L).toSerialized()
    private val viewModel by viewModel<MainViewModel>()
    private val movieAdapter by lazy {
        MovieAdapter { clickEvent(it) }
    }

    private fun clickEvent(url: String) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_URL, url)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()
        initRecyclerView()
        initEvent()
        initCallback()
    }

    private fun initViewModel() {
        binding.vm = viewModel
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = movieAdapter
            addItemDecoration(
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            )
        }
    }

    private fun initEvent() {
        binding.etSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && (keyEvent.action == KeyEvent.ACTION_DOWN)) {
                hideKeyBoard()
                viewModel.searchDataByQuery()
            }

            false
        }

        binding.btEnter.setOnClickListener {
            hideKeyBoard()
            viewModel.searchDataByQuery()
        }

        compositeDisposable += backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe({
                when (it.second - it.first < BACK_BUTTON_TOAST_DURATION) {
                    true -> finish()
                    false -> showBackButtonToast()
                }
            }, {
                showToastMessage("backButtonSubjectDisposable 에러 발생")
            })
    }

    private fun initCallback() {
        viewModel.notificationMsg.observe(this, Observer {
            it.getContentIfNotHandled()?.let { msg -> showToastMessage(msg) }
        })

        val source = Observable.create<CharSequence> { emitter ->
            etSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        emitter.onNext(it)
                    }
                }
            })
        }

        compositeDisposable += source.debounce(QUERY_INPUT_TIME_OUT, TimeUnit.MILLISECONDS)
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread()) //Toast must be running on UI Thread
            .subscribe {
                showToastMessage("$it 검색 중...")
                hideKeyBoard()
                viewModel.searchDataByQuery()
            }
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun showBackButtonToast() {
        showToastMessage("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        backButtonSubject.onNext(System.currentTimeMillis())
    }

    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }

    companion object {
        const val KEY_URL = "KEY_URL"
        const val BACK_BUTTON_TOAST_DURATION = 1500L
        const val QUERY_INPUT_TIME_OUT = 3000L
    }
}
