package com.sample.rxnaversearchapi.view.movie

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.sample.rxnaversearchapi.BR
import com.sample.rxnaversearchapi.R
import com.sample.rxnaversearchapi.base.BaseAdapter
import com.sample.rxnaversearchapi.data.model.MovieItem
import com.sample.rxnaversearchapi.databinding.ActivityMovieBinding
import com.sample.rxnaversearchapi.databinding.ItemMovieBinding
import com.sample.rxnaversearchapi.viewmodel.MovieViewModel
import org.koin.android.ext.android.get

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding
    private val viewModel by lazy { get<MovieViewModel>() }

    private val movieAdapter by lazy {
        object : BaseAdapter<MovieItem, ItemMovieBinding>(R.layout.item_movie, BR.item) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)
        binding.lifecycleOwner = this

        initView()
        initObserver()
    }


    private fun initView() {
        binding.run {
            vm = viewModel
            rvMovie.adapter = movieAdapter

            btnSearch.setOnClickListener {
                "${edtSearch.text}".let { keyWord ->
                    if (keyWord.isNotEmpty()) {
                        viewModel.searchMovie(keyWord)
                    }
                }
            }
        }
    }

    private fun initObserver(){
        viewModel.movieDetailUrl.observe(this, Observer {url ->
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(
                    this@MovieActivity,
                    Uri.parse(url)
                )
        })
    }

    companion object {
        const val TAG = "MovieActivity"
    }
}
