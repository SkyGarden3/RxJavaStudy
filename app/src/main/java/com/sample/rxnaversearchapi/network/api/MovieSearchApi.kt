package com.sample.rxnaversearchapi.network.api

import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieSearchApi {
    @GET("v1/search/movie.json")
    fun getMovieList(
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") secretKey: String,
        @Query("query") keyWord: String
    ): Observable<MovieDataResponse>
}