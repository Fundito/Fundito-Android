package com.fundito.fundito.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.data.service.NetworkClient
import javax.inject.Inject

/**
 * Created by mj on 31, December, 2019
 */
class HomeViewModel @Inject constructor() : ViewModel() {

    val userData = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getUser()
        }.onSuccess {
            emit(it)
        }
    }
}