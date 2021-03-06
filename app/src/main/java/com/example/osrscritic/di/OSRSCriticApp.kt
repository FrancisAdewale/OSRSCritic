package com.example.osrscritic.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class OSRSCriticApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@OSRSCriticApp)
            modules(
                listOf(
                    googleLoginViewModelModule,
                    otherLoginViewModelModule,
                    profileDetailsViewModel,
                    accountDetailsViewModel,
                    mapsFragmentViewModel,
                    displayUserModule,
                    displayUserRepoModule,
                    authModule,
                    firebaseModule,
                    apiModule,
                    retrofitModule,
                )
            )
        }

    }
}