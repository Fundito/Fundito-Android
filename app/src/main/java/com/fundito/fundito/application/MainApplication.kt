package com.fundito.fundito.application

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
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
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)

        initLogging()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

}