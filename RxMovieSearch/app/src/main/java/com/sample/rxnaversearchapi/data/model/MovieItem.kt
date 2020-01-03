package com.sample.rxnaversearchapi.data.model

data class MovieItem(
    val actor: String,
    val director: String,
    val image: String,
    val link: String,
    val pubDate: String,
    val title: String,
    val userRating: String,
    val onClick: (MovieItem)-> Unit
)