package com.fundito.fundito.presentation.main.status

import androidx.lifecycle.*
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class StatusViewModel @Inject constructor() : ViewModel() {

    /**
     * Bottom Sheet이 열린 개수 0~2
     */
    val sheetOpenCount : MutableLiveData<Int> = MutableLiveData(0)

    /**
     * 두번 째 Sheet에서 탭 위치
     */
    val sheet2TabIndex : MutableLiveData<Int> = MutableLiveData(0)

    /**
     * 두번 째 Sheet에서 현재 보여지는 Scene
     */
    val sceneIndex : MutableLiveData<Int> = MutableLiveData(0)


    private val _dispatchBackPressEvent : MutableLiveData<Once<Unit>> = MutableLiveData()
    val dispatchBackPressEvent : LiveData<Once<Unit>> = _dispatchBackPressEvent

    val userData = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getUser()
        }.onSuccess {
            emit(it)
        }
    }

    val fundingData = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getUsingFunditoMoney()
        }.onSuccess {
            emit(it)
        }
    }

    val funditoMoney = liveData {
        kotlin.runCatching {
            NetworkClient.userService.getFunditoMoney()
        }.onSuccess {
            it.getOrNull(0)?.let { emit(it.point) }
        }
    }

    val recentFundingHistories = liveData {
        kotlin.runCatching {
            NetworkClient.fundingService.getMyFundingHistories()
        }.onSuccess {
            emit(it)
        }
    }

    val currentFundingStores = liveData {
        kotlin.runCatching {
            NetworkClient.fundingService.listCurrentFundingStore()
        }.onSuccess {
            emit(it)
        }
    }

    val completeFundingStores = liveData {
        kotlin.runCatching {
            NetworkClient.fundingService.listCompleteFundingStore()
        }.onSuccess {
            emit(it)
        }
    }

    val selectedShopData = MutableLiveData<Pair<Store, Funding>>()

    private val _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading : LiveData<Boolean> = _loading


    fun onClickBack() {
        if(sceneIndex.value == 1) {
            sceneIndex.value = 0
            return
        }
        if((sheetOpenCount.value ?: 0) > 0) {
            _dispatchBackPressEvent.value = Once(Unit)
            return
        }

    }

    fun onSelectStore(storeIdx : Int) {
        viewModelScope.launch {
            _loading.value = true

            kotlin.runCatching {
                val fundings = NetworkClient.fundingService.getMyFundingHistories()
                val funding = fundings.first { it.storeIdx == storeIdx }

                Pair(NetworkClient.storeInfoService.getStoreInfo(storeIdx),funding)
            }.onSuccess {
                selectedShopData.value = it
            }.onFailure {

            }
            _loading.value = false

        }
    }
}