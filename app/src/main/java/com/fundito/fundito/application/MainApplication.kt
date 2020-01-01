package com.fundito.fundito.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fundito.fundito.common.util.WifiReceiver
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
        
        private val _wifiSSID : MutableLiveData<String> = MutableLiveData()
        val wifiSSID : LiveData<String> = _wifiSSID
    }

    private lateinit var wifiReceiver: WifiReceiver

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
       return DaggerAppComponent.builder().app(this).build().also {  it.inject(this) }
    }

    override fun onCreate() {
        super.onCreate()

        GlobalApp = this

        initLogging()
        listenWifiChange()
    }

    private fun initLogging() {
        Timber.plant(Timber.DebugTree())
    }

    private fun listenWifiChange() {
        wifiReceiver = WifiReceiver.register(this, {
            _wifiSSID.value = it ?: ""
        },false)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        WifiReceiver.unregister(this,wifiReceiver,false)
    }

}