package com.sample.rxnaversearchapi.ext

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imgUrl")
fun ImageView.setImageUrl(imgUrl: String){
    Glide.with(context)
        .load(imgUrl)
        .into(this)
}