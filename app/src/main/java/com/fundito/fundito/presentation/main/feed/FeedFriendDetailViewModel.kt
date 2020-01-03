package com.fundito.fundito.presentation.main.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.data.service.NetworkClient
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by mj on 28, December, 2019
 */
class FeedFriendDetailViewModel@Inject constructor(
    @Named("FeedFriendDetailActivityModule_friendIdx")
    private val friendIdx: Int
) : ViewModel() {

    private val _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading : LiveData<Boolean> = _loading

    val items = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.friendService.listFriendFundings(friendIdx)
        }.onSuccess {
            emit(it)
        }.onFailure {
            Timber.e(it)
        }
        _loading.value = false
    }
}