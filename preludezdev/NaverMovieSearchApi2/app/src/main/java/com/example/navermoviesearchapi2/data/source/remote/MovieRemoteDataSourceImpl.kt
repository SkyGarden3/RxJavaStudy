package com.example.navermoviesearchapi2.data.source.remote

import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import com.example.navermoviesearchapi2.network.MovieApiService
import io.reactivex.Single

class MovieRemoteDataSourceImpl(private val api: MovieApiService) : MovieRemoteDataSource {
    override fun getMovieListByName(query: String): Single<MovieListResponse> =
        api.getMovieListByName(query)
}