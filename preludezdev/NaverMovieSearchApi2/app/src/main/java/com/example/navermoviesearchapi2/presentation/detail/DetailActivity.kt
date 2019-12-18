package com.example.navermoviesearchapi2.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.example.navermoviesearchapi2.R
import com.example.navermoviesearchapi2.base.BaseActivity
import com.example.navermoviesearchapi2.databinding.ActivityDetailBinding
import com.example.navermoviesearchapi2.presentation.main.MainActivity

class DetailActivity : BaseActivity<ActivityDetailBinding>(R.layout.activity_detail) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val url = intent.getStringExtra(MainActivity.KEY_URL)
        url?.let { initWebView(it) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String) {
        with(binding.webView) {
            webViewClient = WebViewClient()

            with(settings){
                javaScriptEnabled = true
                cacheMode = WebSettings.LOAD_NO_CACHE
            }

            loadUrl(url)
        }
    }
}