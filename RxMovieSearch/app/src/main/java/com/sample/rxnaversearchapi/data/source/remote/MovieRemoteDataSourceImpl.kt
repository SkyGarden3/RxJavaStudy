package com.sample.rxnaversearchapi.data.source.remote

import android.annotation.SuppressLint
import com.sample.rxnaversearchapi.network.api.MovieSearchApi

class MovieRemoteDataSourceImpl(private val movieSearchApi: MovieSearchApi) :
    MovieRemoteDataSource {

    @SuppressLint("CheckResult")
    override fun getMovieList(keyWord: String) =
        movieSearchApi.getMovieList(keyWord)


}