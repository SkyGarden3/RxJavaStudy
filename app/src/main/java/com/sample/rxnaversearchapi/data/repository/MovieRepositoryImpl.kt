package com.sample.rxnaversearchapi.data.repository

import com.sample.rxnaversearchapi.data.source.remote.MovieRemoteDataSource
import com.sample.rxnaversearchapi.network.model.MovieResponse

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {

    override fun getMovieList(
        keyWord: String,
        callback: (movieResponseList: List<MovieResponse>) -> Unit
    ) {
        movieRemoteDataSource.getMovieList(keyWord, callback)
    }
}