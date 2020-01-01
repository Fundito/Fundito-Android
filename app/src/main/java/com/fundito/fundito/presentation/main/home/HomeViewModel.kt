package com.fundito.fundito.presentation.main.home

import androidx.lifecycle.*
import com.fundito.fundito.broadcast.Broadcast
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.service.NetworkClient
import com.fundito.fundito.data.service.WifiStoreResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 31, December, 2019
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
class HomeViewModel @Inject constructor() : ViewModel() {

    val userData = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getUser()
        }.onSuccess {
            emit(it)
        }
    }
    
    private val _storeConnectWithWifi : MutableLiveData<Boolean> = MutableLiveData(false)
    val storeConnectWithWifi : LiveData<Boolean> = _storeConnectWithWifi

    private val _connectedStoreData : MutableLiveData<WifiStoreResponse> = MutableLiveData()
    val connectedStoreData : LiveData<WifiStoreResponse> = _connectedStoreData

    private val _connectedStoreTimeLineItem : MutableLiveData<Funding> = MutableLiveData()
    val connectedStoreTimeLineItem : LiveData<Funding> = _connectedStoreTimeLineItem

    fun onWifiStateChanged(ssid : String) {
        if(!ssid.isBlank()) {

            viewModelScope.launch {
                kotlin.runCatching {
                    val response = NetworkClient.storeInfoService.getStoreWithSSID(ssid)
                    val timeline = NetworkClient.storeInfoService.listStoreFundingTimeLine(response.storeIdx)
                    response to timeline
                }.onSuccess {
                    _storeConnectWithWifi.value = true
                    _connectedStoreData.value = it.first
                    it.second.getOrNull(0)?.let {
                        _connectedStoreTimeLineItem.value = it
                    }
                }.onFailure {
                    _storeConnectWithWifi.value = false
                }
            }

        }else {
            _storeConnectWithWifi.value = true
        }
    }

    init {
        viewModelScope.launch {
            Broadcast.fundEvent.openSubscription().consumeEach {
                Timber.e("밤ㄴ이ㅓㅁ")
                if(it.first == connectedStoreData.value?.storeIdx) {
                    kotlin.runCatching {
                        NetworkClient.storeInfoService.listStoreFundingTimeLine(it.first)[0]
                    }.onSuccess {
                        _connectedStoreTimeLineItem.value = it
                    }
                }
            }
        }

    }

}