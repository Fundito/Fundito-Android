package com.fundito.fundito.application

import com.fundito.fundito.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by mj on 22, December, 2019
 */
class MainApplication : DaggerApplication() {
    companion object {
        lateinit var GlobalApp : MainApplication
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().app(this).build().also {  it.inject(this) }
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