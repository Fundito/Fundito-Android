package com.fundito.fundito.common.util

import kotlin.math.min

/**
 * Created by mj on 24, December, 2019
 */


/**
 * Money Format으로 콤마가 들어간 문자열에 새로운 문자를 입력할 때 다음 문자열을 반환해준다
 *
 * 예를 들어, 100,000 에 0을 입력하면 1,000,000 을 반환해준다
 */
infix fun String.addCharForMoneyRepresentation(nextNumber : Int) : String {
    return addCharForMoneyRepresentation((nextNumber + '0'.toInt()).toChar())
}
infix fun String.addCharForMoneyRepresentation(nextChar : Char) : String {
    if(nextChar !in '0'..'9')
        throw IllegalArgumentException("nextChar can be only a number ASCI character")

    val src = trim().replace(",","")

    val intValue = src.toLongOrNull() ?: throw IllegalStateException("a src string cannot be converted to integer")

    val newCharLongValue = (nextChar.toLong() - '0'.toLong())

    val lastLongValue = min((intValue * 10) + newCharLongValue,50_000_000L)

    return lastLongValue.toMoney()
}

fun String.removeLatestMoneyCharacter() : String {
    val dropped = dropLast(1)
    return if(dropped.isEmpty()) return "0" else dropped.replace(",","").toLong().toMoney()
}

/**
 * Long값인 숫자에 3단위마다 콤마(,)를 추가해준 문자열을 반환한다.
 *
 * @return A [String] converted with source int value adding comma separation
 */
fun Long.toMoney() : String {
    val src = toString().toMutableList().reversed().toMutableList()

    val willAddedCommaCount = (src.size - 1)/3

    repeat(willAddedCommaCount) {
        src.add((it + 1) * 3 + it,',')
    }

    return src.reversed().joinToString("")
}

fun Int.toMoney() = toLong().toMoney()


fun String.toMoneyLong() : Long {
    return filter {
        it !in listOf(' ',',')
    }.toLongOrNull() ?: 0
}

