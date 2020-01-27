package com.example.navermoviesearchapi2.data

import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import io.reactivex.Single

interface MovieRepository {

    fun getMovieListByName(query: String): Single<MovieListResponse>
}