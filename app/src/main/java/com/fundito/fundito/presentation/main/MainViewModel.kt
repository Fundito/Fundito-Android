package com.fundito.fundito.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class MainViewModel @Inject constructor()  : ViewModel() {


    init {
        viewModelScope.launch {

            runCatching {
            }.onSuccess {
            }.onFailure {
            }

        }

    }

}
