package com.example.navermoviesearchapi2.data.vo


import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("lastBuildDate")
    val lastBuildDate: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("start")
    val start: Int,
    @SerializedName("display")
    val display: Int,
    @SerializedName("items")
    val items: List<Item>
) {
    data class Item(
        @SerializedName("title")
        val title: String,
        @SerializedName("link")
        val link: String,
        @SerializedName("image")
        val image: String,
        @SerializedName("subtitle")
        val subtitle: String,
        @SerializedName("pubDate")
        val pubDate: String,
        @SerializedName("director")
        val director: String,
        @SerializedName("actor")
        val actor: String,
        @SerializedName("userRating")
        val userRating: String
    )
}