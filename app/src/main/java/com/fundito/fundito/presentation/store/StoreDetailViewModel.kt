package com.fundito.fundito.presentation.store

import androidx.lifecycle.*
import com.fundito.fundito.broadcast.Broadcast
import com.fundito.fundito.data.enumerator.FundStatus
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by mj on 26, December, 2019
 */
class StoreDetailViewModel(private val storeIdx : Int) : ViewModel() {


    private val _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading : LiveData<Boolean> = _loading


    //region DATA

    private val _store : MutableLiveData<Store> = MutableLiveData()
    val store : LiveData<Store> = _store
    
    private val _timeLineItems : MutableLiveData<List<Funding>> = MutableLiveData(listOf())
    val timeLineItems : LiveData<List<Funding>> = _timeLineItems

    val menus = store.map {
        it.menus.map { StoreDetailItem(it.name,it.price) }
    }

    val etcs: LiveData<List<StoreDetailItem>> = store.map {
        listOf(
            "영업 시간" to it.businessHour,
            "브레이크 타임" to it.breakTime,
            "휴무" to it.holiday
        )
    }

    //endregion

    //region STATUS
    val investmentActive : LiveData<Boolean> = store.map {
        it.fundStatus == FundStatus.ONGOING
    }
    //endregion

    //region EVENT

    //endregion

    init {


        viewModelScope.launch {
            _loading.value = true
            kotlin.runCatching {
                NetworkClient.storeInfoService.getStoreInfo(storeIdx)
            }.onSuccess {
                _store.value = it
            }.onFailure {
                Timber.e(it)
            }
            _loading.value = false
        }

        viewModelScope.launch {
            while(this.isActive) {
                kotlin.runCatching {
                    NetworkClient.storeInfoService.listStoreFundingTimeLine(storeIdx)
                }.onSuccess {
                    _timeLineItems.value = it
                }.onFailure {
                    Timber.e(it)
                }
                delay(10000L)
            }
        }

        viewModelScope.launch {
            Broadcast.fundEvent.asFlow().collect {
                _loading.value = true
                kotlin.runCatching {
                    NetworkClient.storeInfoService.getStoreInfo(storeIdx)
                }.onSuccess {
                    _store.value = it
                }.onFailure {
                    Timber.e(it)
                }
                _loading.value = false
            }
        }

    }

    fun onWithDraw(storeIdx: Int, rewardMoney: Int) = viewModelScope.launch {
        kotlin.runCatching {
            NetworkClient.userService.withdrawFunditoMoney(storeIdx,rewardMoney)
        }.onSuccess {
            Broadcast.fundEvent.send(-1 to -1)
        }.onFailure {

        }
    }

}