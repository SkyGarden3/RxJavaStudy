package com.sample.rxnaversearchapi.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.rxnaversearchapi.base.BaseAdapter

@BindingAdapter("replaceAll")
fun RecyclerView.replaceAll(list: List<Any>?) {
    @Suppress("UNCHECKED_CAST")
    (adapter as? BaseAdapter<Any, *>)?.replaceAll(list)
}
