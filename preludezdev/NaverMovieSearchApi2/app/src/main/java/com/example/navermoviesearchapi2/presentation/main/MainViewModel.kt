package com.example.navermoviesearchapi2.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navermoviesearchapi2.data.MovieRepository
import com.example.navermoviesearchapi2.data.vo.MovieListResponse
import com.example.navermoviesearchapi2.utils.plusAssign
import com.example.navermoviesearchapi2.utils.withSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val repository: MovieRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _movieList = MutableLiveData<List<MovieListResponse.Item>>()
    val movieList: LiveData<List<MovieListResponse.Item>> get() = _movieList

    private val _notificationMsg = MutableLiveData<Event<String>>()
    val notificationMsg: LiveData<Event<String>> get() = _notificationMsg

    val queryString = MutableLiveData<String>()

    fun searchDataByQuery() {
        val query = queryString.value

        if (query.isNullOrEmpty()) {
            showToastNotificationMsg("검색어를 입력해주세요.")
            return
        }

        compositeDisposable +=
            repository
                .getMovieListByName(query)
                .withSchedulers()
                .subscribe({
                    _movieList.value = it.items
                }, {
                    showToastNotificationMsg("서버 통신에 실패했습니다.")
                })
    }

    private fun showToastNotificationMsg(msg: String) {
        _notificationMsg.value = Event(msg)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
