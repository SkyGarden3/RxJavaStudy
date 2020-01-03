package com.architecture.study.ext

import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.architecture.study.R
import com.architecture.study.data.model.Ticker

@BindingAdapter("compareDiff")
fun TextView.setCompareDiff(ticker: Ticker) {

    text = ticker.compareYesterday.let {
        when {
            it > 0 -> {
                setTextColor(ResourcesCompat.getColor(resources, R.color.colorRed, null))
            }
            it < 0 -> {
                setTextColor(ResourcesCompat.getColor(resources, R.color.colorBlue, null))
            }
            else -> {
                setTextColor(ResourcesCompat.getColor(resources, R.color.colorBlack, null))
            }
        }
        String.format("%.2f%%", it)
    }
}