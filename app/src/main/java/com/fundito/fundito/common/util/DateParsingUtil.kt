package com.fundito.fundito.common.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mj on 26, December, 2019
 */
object DateParsingUtil {

    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
    private val fallbackFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.KOREA)

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
        val date = try{simpleDateFormat.parse(raw) ?: throw RuntimeException()}catch(t:Throwable) {
            fallbackFormat.parse(raw) ?: Date()
        }

        return Calendar.getInstance().apply { time = date }
    }

    fun calculateDiffWithCurrent(rawString : String) : String {
        return calculateDiffWithCurrent(parseString(rawString).timeInMillis)
    }

    fun calculateDiffWithCurrent(timeMs : Long) : String {
        val diff = System.currentTimeMillis() - timeMs
        val diffMin = diff / 1000 / 60

        val dayDiff = diffMin / 60 / 24
        val hourDiff = diffMin / 60

        return when {
            dayDiff > 0 -> "${dayDiff}일전"
            hourDiff > 0 ->"${hourDiff}시간전"
            else -> "${diffMin}분전"
        }
    }

}