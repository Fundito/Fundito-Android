package com.fundito.fundito.presentation.funding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.data.service.NetworkClient
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by mj on 01, January, 2020
 */
class FundingViewModel @Inject constructor(
    @Named("FundingActivity_storeIdx")
    private val storeIdx: Int
) : ViewModel() {

    private val _loading : MutableLiveData<Boolean> = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading

    val cardData = liveData {
        kotlin.runCatching {
            NetworkClient.cardService.getCard()
        }.onSuccess {
            emit(it)
        }
    }

    val funditoMyMoney = liveData<Int> {
        kotlin.runCatching {
            NetworkClient.userService.getFunditoMoney()
        }.onSuccess {
            emit(it.getOrNull(0)?.point ?: 0)
        }
    }

    val inputMoney = MutableLiveData<Int>()

}