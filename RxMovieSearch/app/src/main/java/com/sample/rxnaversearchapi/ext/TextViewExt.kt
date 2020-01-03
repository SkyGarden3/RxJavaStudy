package com.sample.rxnaversearchapi.ext

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("htmlText")
fun TextView.setHtmlText(value: String){
    text = Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
}