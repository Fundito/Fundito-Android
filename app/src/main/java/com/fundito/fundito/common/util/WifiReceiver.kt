package com.fundito.fundito.common.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fundito.fundito.application.MainApplication.Companion.GlobalApp

/**
 * Created by mj on 01, January, 2020
 */
class WifiReceiver : BroadcastReceiver() {

    companion object {

        private const val ACTION_WIFI = WifiManager.NETWORK_STATE_CHANGED_ACTION

        fun register(context: Context, listener : ((String?) -> Unit)? = null, withLocal: Boolean = true): WifiReceiver {
            return WifiReceiver().also { receiver ->
                receiver.listener = listener

                if (!withLocal) {
                    context.registerReceiver(receiver, provideIntentFilter())
                } else {
                    LocalBroadcastManager.getInstance(context).registerReceiver(receiver, provideIntentFilter())
                }
            }
        }

        fun unregister(context: Context, receiver: WifiReceiver, withLocal: Boolean = true) {
            if (!withLocal) {
                context.unregisterReceiver(receiver)
            } else {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
            }
        }


        private fun provideIntentFilter(): IntentFilter {
            return IntentFilter(ACTION_WIFI)
        }
    }

    private var listener : ((String?) -> Unit)? = null

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == ACTION_WIFI) {
            val networkInfo = intent.getParcelableExtra<NetworkInfo>(WifiManager.EXTRA_NETWORK_INFO)
            if(networkInfo?.type == ConnectivityManager.TYPE_WIFI) {
                val wifiManager = GlobalApp.getSystemService(Context.WIFI_SERVICE) as? WifiManager
                val wifiInfo = wifiManager?.connectionInfo
                val ssid = wifiInfo?.ssid
                listener?.invoke(ssid)
            }

        }
    }
}