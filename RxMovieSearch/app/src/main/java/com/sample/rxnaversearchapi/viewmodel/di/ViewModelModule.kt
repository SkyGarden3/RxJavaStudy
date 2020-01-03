package com.sample.rxnaversearchapi.viewmodel.di

import com.sample.rxnaversearchapi.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
}