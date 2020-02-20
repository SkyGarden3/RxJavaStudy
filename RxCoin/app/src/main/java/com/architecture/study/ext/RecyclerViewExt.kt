package com.architecture.study.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.architecture.study.base.BaseAdapter

@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseAdapter<Any, *>)?.replaceAll(list)
}

