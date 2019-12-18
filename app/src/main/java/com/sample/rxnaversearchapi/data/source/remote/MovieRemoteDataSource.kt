package com.sample.rxnaversearchapi.data.source.remote

import com.sample.rxnaversearchapi.network.model.MovieResponse

interface MovieRemoteDataSource {
    fun getMovieList(
        keyWord: String,
        callback: (movieResponseList: List<MovieResponse>) -> Unit
    )
}