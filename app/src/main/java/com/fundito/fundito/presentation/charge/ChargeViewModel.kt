package com.fundito.fundito.presentation.charge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class ChargeViewModel @Inject constructor() : ViewModel() {
    val chargeMoney : MutableLiveData<Long> = MutableLiveData(0L)

    val visiblePlaceHolder = chargeMoney.map {
        it == 0L
    }
}