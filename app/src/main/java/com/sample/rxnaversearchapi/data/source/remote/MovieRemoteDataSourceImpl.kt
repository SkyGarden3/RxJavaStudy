package com.sample.rxnaversearchapi.data.source.remote

import android.util.Log
import com.sample.rxnaversearchapi.network.api.MovieSearchApi
import com.sample.rxnaversearchapi.network.api.NaverApiKey
import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import com.sample.rxnaversearchapi.network.model.MovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MovieRemoteDataSourceImpl(private val movieSearchApi: MovieSearchApi) :
    MovieRemoteDataSource {
    override fun getMovieList(
        keyWord: String,
        callback: (movieResponseList: List<MovieResponse>) -> Unit
    ) {
        val responseObservable =
            movieSearchApi.getMovieList(
                NaverApiKey.CLIENT_ID,
                NaverApiKey.SECRET_KEY,
                keyWord
            )

        responseObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<MovieDataResponse>() {
                override fun onNext(response: MovieDataResponse) {

                    callback(response.movieResponseList)
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {

                }
            })
    }

}