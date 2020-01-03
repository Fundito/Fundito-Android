package com.fundito.fundito.common.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mj on 26, December, 2019
 */
object DateParsingUtil {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
    private val fallbackFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.KOREA)
    private val fallback2Format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.KOREA)
    private val fallback3Format = SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.KOREA)

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
        val date = try{
            simpleDateFormat.parse(raw)
        }catch(t:Throwable) {
            try {
                fallbackFormat.parse(raw)
            }catch(t : Throwable) {
                try {
                    fallback2Format.parse(raw)
                }catch (t: Throwable) {
                    try {
                        fallback3Format.parse(raw)
                    }catch(t: Throwable) {
                        throw RuntimeException()
                    }
                }
            }
        }

        return Calendar.getInstance().apply { time = date }
    }

    fun calculateDiffWithCurrent(rawString : String, isFuture : Boolean = false) : String {
        return calculateDiffWithCurrent(parseString(rawString).timeInMillis,isFuture)
    }

    fun calculateDiffWithCurrent(timeMs : Long, isFuture : Boolean = false) : String {
        var diff = System.currentTimeMillis() - timeMs

        if(isFuture) diff = -diff
        val diffMin = diff / 1000 / 60

        val dayDiff = diffMin / 60 / 24
        val hourDiff = diffMin / 60

        return when {
            dayDiff > 0 -> "${dayDiff}일전"
            hourDiff > 0 ->"${hourDiff}시간전"
            diffMin > 0 -> "${diffMin}분전"
            else -> if(!isFuture) "방금 전" else "마감"
        }
    }

}