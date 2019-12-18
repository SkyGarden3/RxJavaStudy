package com.example.navermoviesearchapi2.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navermoviesearchapi2.R
import com.example.navermoviesearchapi2.base.BaseActivity
import com.example.navermoviesearchapi2.databinding.ActivityMainBinding
import com.example.navermoviesearchapi2.presentation.detail.DetailActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val backButtonSubject: Subject<Long> = BehaviorSubject.createDefault(0L).toSerialized()
    private val backButtonSubjectDisposable: Disposable =
        backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .map { it[0] to it[1] }
            .subscribe({
                when (it.second - it.first < TOAST_DURATION) {
                    true -> finish()
                    false -> showBackButtonToast()
                }
            }, {
                showToastMessage("backButtonSubjectDisposable 에러 발생")
            })

    private val movieAdapter by lazy {
        MovieAdapter { clickEvent(it) }
    }
    private val viewModel by viewModel<MainViewModel>()

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
    }

    private fun initCallback() {
        viewModel.notificationMsg.observe(this, Observer {
            it.getContentIfNotHandled()?.let { msg -> showToastMessage(msg) }
        })
    }

    private fun hideKeyBoard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun clickEvent(url: String) {
        startActivity(Intent(this, DetailActivity::class.java).apply {
            putExtra(KEY_URL, url)
        })
    }

    private fun showBackButtonToast() {
        showToastMessage("뒤로가기 버튼을 한번 더 누르면 종료됩니다.")
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        backButtonSubject.onNext(System.currentTimeMillis())
    }

    override fun onDestroy() {
        super.onDestroy()
        backButtonSubjectDisposable.dispose()
    }

    companion object {
        const val KEY_URL = "KEY_URL"
        const val TOAST_DURATION = 1500L
    }
}
