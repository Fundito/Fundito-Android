package com.fundito.fundito.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by mj on 01, January, 2020
 */
class LoginPasswordViewModel @Inject constructor() : ViewModel() {

    val password = MutableLiveData("")
}