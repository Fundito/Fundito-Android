package com.fundito.fundito.presentation.main.status

import androidx.lifecycle.*
import com.fundito.fundito.broadcast.Broadcast
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.CurrentFundingResponse
import com.fundito.fundito.data.service.CurrentFundingStatus
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
@UseExperimental(ExperimentalCoroutinesApi::class)
class StatusViewModel @Inject constructor() : ViewModel() {

    /**
     * Bottom Sheet이 열린 개수 0~2
     */
    val sheetOpenCount: MutableLiveData<Int> = MutableLiveData(0)

    /**
     * 두번 째 Sheet에서 탭 위치
     */
    val sheet2TabIndex: MutableLiveData<Int> = MutableLiveData(0)

    /**
     * 두번 째 Sheet에서 현재 보여지는 Scene
     */
    val sceneIndex: MutableLiveData<Int> = MutableLiveData(0)


    private val _dispatchBackPressEvent: MutableLiveData<Once<Unit>> = MutableLiveData()
    val dispatchBackPressEvent: LiveData<Once<Unit>> = _dispatchBackPressEvent

    val userData = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.userService.getUser()
        }.onSuccess {
            emit(it)
        }
        _loading.value = false
    }

    private val _fundingData: MutableLiveData<CurrentFundingStatus> = MutableLiveData()
    val fundingData: LiveData<CurrentFundingStatus> = _fundingData


    private val _funditoMoney: MutableLiveData<Int> = MutableLiveData()
    val funditoMoney: LiveData<Int> = _funditoMoney


    private val _recentFundingHistories: MutableLiveData<List<RecentFundingItem>> = MutableLiveData(listOf())
    val recentFundingHistories: LiveData<List<RecentFundingItem>> = _recentFundingHistories

    private val _currentFundingStores: MutableLiveData<List<CurrentFundingResponse>> = MutableLiveData(listOf())
    val currentFundingStores: LiveData<List<CurrentFundingResponse>> = _currentFundingStores

    val completeFundingStores = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.fundingService.listCompleteFundingStore()
        }.onSuccess {
            emit(it)
        }
        _loading.value = false
    }

    val selectedShopData = MutableLiveData<Triple<Store, Funding, List<Funding>>>()

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    init {
        getFunditoMoney()
        getFundingData()
        getRecentFundingHistories()
        getCurrentFundings()

        viewModelScope.launch {
            Broadcast.chargeCompleteEvent.asFlow().collect {
                Timber.e("충전 완료 $it")
                getFunditoMoney()
                getFundingData()
                getRecentFundingHistories()
                getCurrentFundings()
            }
        }
        viewModelScope.launch {
            Broadcast.fundEvent.asFlow().collect {
                Timber.e("펀딩 완료 ${it.first} - ${it.second}")
                getFunditoMoney()
                getFundingData()
                getRecentFundingHistories()
                getCurrentFundings()
            }
        }


    }

    fun getFunditoMoney() {
        viewModelScope.launch {
            _loading.value = true
            kotlin.runCatching {
                NetworkClient.userService.getFunditoMoney()
            }.onSuccess {
                it.getOrNull(0)?.let {
                    _funditoMoney.value = it.point
                }
            }
            _loading.value = false
        }
    }

    private fun getFundingData() = viewModelScope.launch {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.userService.getUsingFunditoMoney()
        }.onSuccess {
            _fundingData.value = it
        }
        _loading.value = false
    }

    private fun getRecentFundingHistories() = viewModelScope.launch {
        _loading.value = true
        kotlin.runCatching {
            val list = NetworkClient.fundingService.getMyFundingHistories()
            val store = NetworkClient.storeInfoService.getStoreInfo(list[0].storeIdx)
            list.map { store to it }
        }.onSuccess {
            _recentFundingHistories.value = it
        }
        _loading.value = false
    }

    private fun getCurrentFundings() = viewModelScope.launch {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.fundingService.listCurrentFundingStore()
        }.onSuccess {
            Timber.e("ASDSAD : $it")
            _currentFundingStores.value = it
        }.onFailure {
            Timber.e(it)
        }
        _loading.value = false
    }


    fun onClickBack() {
        if (sceneIndex.value == 1) {
            sceneIndex.value = 0
            return
        }
        if ((sheetOpenCount.value ?: 0) > 0) {
            _dispatchBackPressEvent.value = Once(Unit)
            return
        }

    }

    fun onSelectStore(storeIdx: Int) {
        viewModelScope.launch {
            _loading.value = true

            kotlin.runCatching {
                val fundings = NetworkClient.fundingService.getMyFundingHistories()
                val funding = fundings.first { it.storeIdx == storeIdx }
                Triple(NetworkClient.storeInfoService.getStoreInfo(storeIdx), funding, fundings)
            }.onSuccess {
                selectedShopData.value = it
            }.onFailure {

            }
            _loading.value = false

        }
    }
}