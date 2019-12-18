package com.sample.rxnaversearchapi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.rxnaversearchapi.data.model.MovieItem
import com.sample.rxnaversearchapi.data.repository.MovieRepository

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    val movieItemList = MutableLiveData<List<MovieItem>>()
    val movieDetailUrl = MutableLiveData<String>()

    private val onItemClickEvent: (MovieItem) -> Unit = { clickedItem ->
        movieDetailUrl.value = clickedItem.link
    }

    fun searchMovie(keyWord: String) {
        movieRepository.getMovieList(keyWord) { movieResponseList ->
            movieItemList.value = movieResponseList.map {
                it.toMovieItem(onItemClickEvent)
            }
        }
    }

}