package com.fundito.fundito.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class MainViewModel @Inject constructor()  : ViewModel() {

    private val _menuIndex : MutableLiveData<Int> = MutableLiveData(0)
    val menuIndex : LiveData<Int> = _menuIndex

}
