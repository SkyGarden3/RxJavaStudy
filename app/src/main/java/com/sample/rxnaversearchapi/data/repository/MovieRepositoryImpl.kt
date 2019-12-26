package com.sample.rxnaversearchapi.data.repository

import com.sample.rxnaversearchapi.data.source.remote.MovieRemoteDataSource
import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import io.reactivex.Single

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) :
    MovieRepository {


    override fun getMovieList(keyWord: String): Single<MovieDataResponse> =
        movieRemoteDataSource.getMovieList(keyWord)
}