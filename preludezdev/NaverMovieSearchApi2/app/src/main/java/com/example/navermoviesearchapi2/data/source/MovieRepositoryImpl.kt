package com.example.navermoviesearchapi2.data.source

import com.example.navermoviesearchapi2.data.MovieRepository
import com.example.navermoviesearchapi2.data.source.remote.MovieRemoteDataSource
import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import io.reactivex.Single

class MovieRepositoryImpl(private val movieRemoteDataSource: MovieRemoteDataSource) : MovieRepository {

    override fun getMovieListByName(query: String): Single<MovieListResponse> =
        movieRemoteDataSource.getMovieListByName(query)
}