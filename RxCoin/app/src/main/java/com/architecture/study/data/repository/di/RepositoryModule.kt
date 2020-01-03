package com.architecture.study.data.repository.di

import com.architecture.study.data.repository.UpbitRepository
import com.architecture.study.data.repository.UpbitRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UpbitRepository> { UpbitRepositoryImpl(get()) }
}