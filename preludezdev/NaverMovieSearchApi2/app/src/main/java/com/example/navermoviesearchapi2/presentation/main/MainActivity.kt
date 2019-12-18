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
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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

    companion object {
        const val KEY_URL = "KEY_URL"
    }
}
