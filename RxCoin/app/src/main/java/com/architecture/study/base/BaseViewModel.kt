package com.architecture.study.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architecture.study.util.Event
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val _exceptionMessage = MutableLiveData<Event<String>>()
    val exceptionMessage: LiveData<Event<String>>
        get() = _exceptionMessage
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}