package com.fundito.fundito.common.util

/**
 * Created by mj on 22, December, 2019
 */
object NativeUtil {
    init { System.loadLibrary("native") }

    external fun sayHello() : String
}