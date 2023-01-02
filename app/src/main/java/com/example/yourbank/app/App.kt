package com.example.yourbank.app

import android.app.Application
import com.example.yourbank.di.applicationModule
import com.example.yourbank.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(applicationModule, viewModelModule)
            )
        }
    }
}