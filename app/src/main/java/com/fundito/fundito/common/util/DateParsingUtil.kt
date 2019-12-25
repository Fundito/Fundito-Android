package com.fundito.fundito.common.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mj on 26, December, 2019
 */
object DateParsingUtil {

    private val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm:ss", Locale.KOREA)

    fun parseToYMD(src : Calendar, separator : String = ".") : String {
        val year = src[Calendar.YEAR]
        val month = src[Calendar.MONTH] + 1
        val date = src[Calendar.DATE]
        return String.format("%04d",year) + separator + String.format("%02d",month) + separator + String.format("%02d",date)
    }

    fun parseToYMDHM(src : Calendar, ymdSeparator: String = ".", timeSeparator : String = ":") : String {
        val ymd = parseToYMD(src,ymdSeparator)
        val hour = src[Calendar.HOUR_OF_DAY]
        val minute = src[Calendar.MINUTE]
        return ymd + " " + String.format("%02d",hour) + timeSeparator + String.format("%02d",minute)
    }

    fun parseString(raw : String) : Calendar {
        val date = simpleDateFormat.parse(raw)

        return Calendar.getInstance().apply { time = date!! }
    }

}