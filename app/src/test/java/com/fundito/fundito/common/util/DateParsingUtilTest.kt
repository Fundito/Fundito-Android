package com.fundito.fundito.common.util

import com.google.common.truth.Truth
import org.junit.Test
import java.util.*

/**
 * Created by mj on 30, December, 2019
 */
class DateParsingUtilTest {

    private val sut = DateParsingUtil

    @Test
    fun asd() {
        /**
         * This test is valid until 2019
         */
        val result = DateParsingUtil.parseString("2019-12-30T03:09:44.000-0700")
        Truth.assertThat(result[Calendar.YEAR]).isEqualTo(2019)
    }

    @Test
    fun calculateDiffWithCurrent() {
        /**
         * Crazy Temporary Test
         */
        Truth.assertThat(sut.calculateDiffWithCurrent("2019-12-30 01:26:44")).isEqualTo("2시간전")
    }

}