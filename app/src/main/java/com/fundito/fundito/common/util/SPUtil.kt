package com.fundito.fundito.common.util

import android.content.Context
import com.fundito.fundito.application.MainApplication.Companion.GlobalApp

/**
 * Created by mj on 26, December, 2019
 */
object SPUtil {

    private val packageName = GlobalApp.packageName

    private val preferences = GlobalApp.getSharedPreferences(packageName, Context.MODE_PRIVATE)


    var accessToken : String
    get() = preferences.getString("accessToken","") ?: ""
    set(value) = preferences.edit().putString("accessToken",value).apply()

}