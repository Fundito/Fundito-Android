package com.fundito.fundito.common.util

import androidx.annotation.Px
import com.fundito.fundito.application.MainApplication.Companion.GlobalApp

/**
 * Created by mj on 26, December, 2019
 */
@Px
fun Int.toPixel() =  GlobalApp.resources.displayMetrics.density * this