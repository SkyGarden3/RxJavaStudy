package com.example.navermoviesearchapi2.network

import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApiService {

    @Headers(
        "X-Naver-Client-Id: n9S7GbbjoVsXLWJdZEus",
        "X-Naver-Client-Secret: regEDQ1Wxd"
    )
    @GET("v1/search/movie.json")
    fun getMovieListByName(@Query("query") query: String): Single<MovieListResponse>
}