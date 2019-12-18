package com.example.navermoviesearchapi2.di

import com.example.navermoviesearchapi2.data.MovieRepository
import com.example.navermoviesearchapi2.data.source.MovieRepositoryImpl
import com.example.navermoviesearchapi2.data.source.remote.MovieRemoteDataSource
import com.example.navermoviesearchapi2.data.source.remote.MovieRemoteDataSourceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single { MovieRepositoryImpl(get(named("Remote"))) as MovieRepository }
    single(named("Remote")) { MovieRemoteDataSourceImpl(get()) as MovieRemoteDataSource }
}