package com.architecture.study.domain.di

import com.architecture.study.domain.usecase.GetAllTicker
import com.architecture.study.domain.usecase.GetTicker
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {

    factory { (currentExchange: String) -> GetAllTicker(get(named(currentExchange))) }
    single { GetTicker(get()) }
}