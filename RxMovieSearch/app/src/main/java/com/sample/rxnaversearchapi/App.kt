package com.sample.rxnaversearchapi

import android.app.Application
import android.content.Context
import com.sample.rxnaversearchapi.data.repository.di.repositoryModule
import com.sample.rxnaversearchapi.data.source.di.sourceModule
import com.sample.rxnaversearchapi.network.di.networkModule
import com.sample.rxnaversearchapi.viewmodel.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }


    override fun onCreate() {
        super.onCreate()
        instance = this
        setUpKoin()

    }

    private fun setUpKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    sourceModule,
                    viewModelModule
                )
            )
        }
    }

    fun context(): Context = applicationContext

}