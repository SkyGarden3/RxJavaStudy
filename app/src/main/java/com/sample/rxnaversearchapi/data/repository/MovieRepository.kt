package com.sample.rxnaversearchapi.data.repository

import com.sample.rxnaversearchapi.network.model.MovieResponse

interface MovieRepository {
    fun getMovieList(
        keyWord: String,
        callback: (movieResponseList: List<MovieResponse>) -> Unit
    )
}