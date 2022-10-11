package com.fathan.madegithub

import android.app.Application
import com.fathan.core2.di.databaseModule
import com.fathan.core2.di.networkModule
import com.fathan.core2.di.repositoryModule
import com.fathan.di.*
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        LeakCanary.newLeakDisplayActivityIntent()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}