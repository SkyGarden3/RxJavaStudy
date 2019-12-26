package com.sample.rxnaversearchapi.data.repository

import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import io.reactivex.Single

interface MovieRepository {
    fun getMovieList(keyWord: String): Single<MovieDataResponse>
}