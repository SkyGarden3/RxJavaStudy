package com.architecture.study.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ITEM : Any, B : ViewDataBinding>(
    @LayoutRes
    private val layoutRes: Int,
    private val dataBindingId: Int? = null
) : RecyclerView.Adapter<BaseViewHolder<B>>() {
    private val items = mutableListOf<ITEM>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : BaseViewHolder<B>(
            layoutRes = layoutRes,
            parent = parent,
            dataBindingId = dataBindingId
        ) {}

    override fun getItemCount(): Int =
        items.size

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) =
        holder.bind(items[position])

    fun replaceAll(items: List<ITEM>?) {
        if(items != null){
            this.items.run {
                clear()
                addAll(items)
            }
            notifyDataSetChanged()
        }
    }
}