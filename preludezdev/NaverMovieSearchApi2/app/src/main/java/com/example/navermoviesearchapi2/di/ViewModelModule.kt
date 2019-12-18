package com.example.navermoviesearchapi2.di

import com.example.navermoviesearchapi2.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{ MainViewModel(get()) }
}