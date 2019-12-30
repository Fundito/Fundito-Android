package com.fundito.fundito.presentation.noti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.data.service.NotificationResponse
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 29, December, 2019
 */
class NotiViewModel @Inject constructor() : ViewModel() {

    private val _notiItems : MutableLiveData<List<NotificationResponse>> = MutableLiveData(listOf())
    val notiItems : LiveData<List<NotificationResponse>> = _notiItems


    init {
        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.notificationService.listNotis()
            }.onSuccess {
                _notiItems.value = it
            }.onFailure {
                Timber.e(it)
            }
        }

    }
}