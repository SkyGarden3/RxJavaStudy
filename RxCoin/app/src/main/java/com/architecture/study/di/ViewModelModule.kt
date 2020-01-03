package com.architecture.study.di

import com.architecture.study.viewmodel.TickerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (baseCurrency: String) -> TickerViewModel(get(), baseCurrency) }
}