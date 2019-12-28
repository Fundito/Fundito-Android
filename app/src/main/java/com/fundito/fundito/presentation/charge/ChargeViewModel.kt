package com.fundito.fundito.presentation.charge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.fundito.fundito.common.widget.Once
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class ChargeViewModel @Inject constructor() : ViewModel() {
    val chargeMoney : MutableLiveData<Long> = MutableLiveData(0L)

    val visiblePlaceHolder = chargeMoney.map {
        it == 0L
    }

    private val _passwordMatch : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val passwordMatch : LiveData<Once<Boolean>> = _passwordMatch

    fun onTypedPasswordComplete(password: String) {
        //TODO
        _passwordMatch.value = Once(true)
    }
}