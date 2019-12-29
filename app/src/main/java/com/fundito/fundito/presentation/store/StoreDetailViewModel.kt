package com.fundito.fundito.presentation.store

import androidx.lifecycle.*
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by mj on 26, December, 2019
 */
class StoreDetailViewModel(private val storeIdx : Int) : ViewModel() {


    //region STATUS

    //endregion

    //region DATA

    private val _store : MutableLiveData<Store> = MutableLiveData()
    val store : LiveData<Store> = _store

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

    //region EVENT

    //endregion

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.storeInfoService.getStoreInfo(8)//TODO
            }.onSuccess {
                _store.value = it
            }.onFailure {
                Timber.e(it)
            }
        }
    }


}