package com.fundito.fundito.common.util

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

/**
 * Created by mj on 27, December, 2019
 */
inline fun <reified T> AppCompatActivity.startActivity(clazz: KClass<out T>, vararg params: Any) where T : AppCompatActivity{
    val intent = Intent(this,clazz.java)
    startActivity(intent)
}