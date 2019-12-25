package com.fundito.fundito.application

import android.app.Application
import timber.log.Timber

/**
 * Created by mj on 22, December, 2019
 */
class MainApplication : Application() {

    companion object {
        lateinit var GlobalApp : MainApplication
    }

    override fun onCreate() {
        super.onCreate()

        GlobalApp = this


        initLogging()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

}