package com.fundito.fundito.application

import android.app.Application
import timber.log.Timber

/**
 * Created by mj on 22, December, 2019
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogging()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

}