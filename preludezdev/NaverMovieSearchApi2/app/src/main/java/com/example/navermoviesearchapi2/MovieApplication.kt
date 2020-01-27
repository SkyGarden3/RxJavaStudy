package com.example.navermoviesearchapi2

import android.app.Application
import com.example.navermoviesearchapi2.di.networkModule
import com.example.navermoviesearchapi2.di.repositoryModule
import com.example.navermoviesearchapi2.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {

    private val moduleList = listOf(repositoryModule, networkModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@MovieApplication)
            modules(moduleList)
        }
    }
}