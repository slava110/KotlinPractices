package com.slava_110.kotlinpractices

import android.app.Application
import com.slava_110.kotlinpractices.di.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KPApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@KPApplication)
            modules(commonModule())
        }
    }
}