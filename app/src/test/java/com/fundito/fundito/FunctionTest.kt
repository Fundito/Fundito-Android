package com.fundito.fundito

import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.toMoney
import com.google.common.truth.Truth
import org.junit.Test

/**
 * Created by mj on 24, December, 2019
 */
class FunctionTest {

    @Test
    fun `Int to Money String`() {
        Truth.assertThat(1.toMoney()).isEqualTo("1")
        Truth.assertThat(10.toMoney()).isEqualTo("10")
        Truth.assertThat(100.toMoney()).isEqualTo("100")
        Truth.assertThat(1_000.toMoney()).isEqualTo("1,000")
        Truth.assertThat(10_000.toMoney()).isEqualTo("10,000")
        Truth.assertThat(100_000.toMoney()).isEqualTo("100,000")
        Truth.assertThat(1_000_000.toMoney()).isEqualTo("1,000,000")
        Truth.assertThat(1_000_000_000.toMoney()).isEqualTo("1,000,000,000")
    }

    @Test
    fun `add a character to string makes money format string`() {
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat(" 100  " addCharForMoneyRepresentation '1').isEqualTo("1,001")
        Truth.assertThat("3,600" addCharForMoneyRepresentation '1').isEqualTo("36,001")
        Truth.assertThat("100,000" addCharForMoneyRepresentation '0').isEqualTo("1,000,000")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
        Truth.assertThat("0" addCharForMoneyRepresentation '1').isEqualTo("1")
    }
}