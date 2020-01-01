package com.fundito.fundito.presentation.main.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.data.service.NetworkClient
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class FeedFriendDetailViewModel @Inject constructor() : ViewModel() {

    val userData = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getUser()
        }.onSuccess {
            emit(it)
        }
    }
}