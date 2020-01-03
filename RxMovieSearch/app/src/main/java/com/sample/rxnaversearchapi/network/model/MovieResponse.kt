package com.sample.rxnaversearchapi.network.model


import com.google.gson.annotations.SerializedName
import com.sample.rxnaversearchapi.data.model.MovieItem

data class MovieResponse(
    @SerializedName("actor")
    val actor: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("pubDate")
    val pubDate: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userRating")
    val userRating: String
) {
    fun toMovieItem(onClick: (MovieItem) -> Unit) =
        MovieItem(
            actor,
            director,
            image,
            link,
            pubDate,
            title,
            userRating,
            onClick
        )
}