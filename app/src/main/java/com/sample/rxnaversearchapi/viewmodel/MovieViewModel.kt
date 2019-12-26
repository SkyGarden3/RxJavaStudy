package com.sample.rxnaversearchapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.rxnaversearchapi.base.BaseViewModel
import com.sample.rxnaversearchapi.data.model.MovieItem
import com.sample.rxnaversearchapi.data.repository.MovieRepository
import com.sample.rxnaversearchapi.ext.plusAssign
import com.sample.rxnaversearchapi.network.model.MovieDataResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {

    private val _movieItemList = MutableLiveData<List<MovieItem>>()
    val movieItemList: LiveData<List<MovieItem>> get() = _movieItemList
    private val _movieDetailUrl = MutableLiveData<String>()
    val movieDetailUrl: LiveData<String> get() = _movieDetailUrl

    private val onItemClickEvent: (MovieItem) -> Unit = { clickedItem ->
        _movieDetailUrl.value = clickedItem.link
    }

    fun searchMovie(keyWord: String) {
        compositeDisposable += movieRepository.getMovieList(keyWord)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<MovieDataResponse>() {
                override fun onSuccess(t: MovieDataResponse) {
                    _movieItemList.value = t.movieResponseList.map { movieResponse ->
                        movieResponse.toMovieItem(onItemClickEvent)
                    }
                }

                override fun onError(e: Throwable) {

                }
            })
    }
}