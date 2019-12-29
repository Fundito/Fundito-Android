package com.fundito.fundito.presentation.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.NetworkClient

/**
 * Created by mj on 26, December, 2019
 */
class StoreDetailViewModel(private val storeIdx : Int) : ViewModel() {


    //region STATUS

    //endregion

    //region DATA

    val store = liveData<Store> {
        this.emit(NetworkClient.storeInfoService.getStoreInfo(8))
    }

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

    }


}