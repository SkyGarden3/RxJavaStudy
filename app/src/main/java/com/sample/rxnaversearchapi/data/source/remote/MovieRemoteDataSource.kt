package com.sample.rxnaversearchapi.data.source.remote

import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import io.reactivex.Single

interface MovieRemoteDataSource {
    fun getMovieList(keyWord: String): Single<MovieDataResponse>
}