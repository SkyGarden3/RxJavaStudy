package com.example.navermoviesearchapi2.di

import com.example.navermoviesearchapi2.data.MovieRepository
import com.example.navermoviesearchapi2.data.source.MovieRepositoryImpl
import com.example.navermoviesearchapi2.data.source.remote.MovieRemoteDataSource
import com.example.navermoviesearchapi2.data.source.remote.MovieRemoteDataSourceImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get()) }
    single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get()) }
}
