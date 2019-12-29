package com.fundito.fundito.presentation.noti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by mj on 29, December, 2019
 */
class NotiViewModel @Inject constructor() : ViewModel() {

    private val _notiItems : MutableLiveData<List<String>> = MutableLiveData(listOf("","",""))
    val notiItems : LiveData<List<String>> = _notiItems


}