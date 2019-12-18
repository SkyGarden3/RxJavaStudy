package com.example.navermoviesearchapi2.data.source.remote

import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import io.reactivex.Single


interface MovieRemoteDataSource{

    fun getMovieListByName(query: String): Single<MovieListResponse>
}