package com.fundito.fundito.presentation.funding

import androidx.lifecycle.*
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by mj on 01, January, 2020
 */
class FundingViewModel @Inject constructor(
    @Named("FundingActivity_storeIdx")
    private val storeIdx: Int,
    @Named("FundingActivity_refundPercent")
    val refundPercent: Int
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

    val refundMoney = inputMoney.map {
        it * (refundPercent - 100f) * 0.01f
    }

    val totalMoney = refundMoney.map {
        it + (inputMoney.value ?: 0)
    }

    val store = liveData {
        kotlin.runCatching {
            NetworkClient.storeInfoService.getStoreInfo(storeIdx)
        }.onSuccess {
            emit(it)
        }
    }

    val password = MutableLiveData<String>("")

    private val _fundResult : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val fundResult : LiveData<Once<Boolean>> = _fundResult

    fun onCheckPasswordMatch() {
        val password = password.value ?: return
        val inputMoney = inputMoney.value ?: return
        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.fundingService.fundWithPassword(password,storeIdx,inputMoney)
            }.onSuccess {
                _fundResult.value = Once(true)
            }.onFailure {
                _fundResult.value = Once(false)
            }
        }
    }
}