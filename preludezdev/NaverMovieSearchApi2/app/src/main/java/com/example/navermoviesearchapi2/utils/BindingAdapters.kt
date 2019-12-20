package com.example.navermoviesearchapi2.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.navermoviesearchapi2.R
import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import com.example.navermoviesearchapi2.presentation.main.MovieAdapter

@BindingAdapter("setImageUrl")
fun ImageView.setImageUrl(url: String?) {
    if (url.isNullOrEmpty()) return

    Glide.with(this)
        .load(url)
        .placeholder(R.drawable.loading)
        .error(R.drawable.image_not_found)
        .into(this)
}

@BindingAdapter("setData")
fun RecyclerView.setData(list: List<MovieListResponse.Item>?) {
    (adapter as? MovieAdapter)?.setData(list)
}