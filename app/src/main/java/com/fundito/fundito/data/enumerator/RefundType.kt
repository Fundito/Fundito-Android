package com.fundito.fundito.data.enumerator

/**
 * Created by mj on 30, December, 2019
 */
enum class RefundType (val refundPercent : Int) {
    REFUND_150(150),
    REFUND_175(175),
    REFUND_200(200)

    ;

    companion object {
        fun parseValue(value : Int) = values().firstOrNull { value == it.refundPercent } ?: REFUND_150
    }
}