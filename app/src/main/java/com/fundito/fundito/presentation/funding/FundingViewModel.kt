package com.fundito.fundito.presentation.funding

import androidx.lifecycle.*
import com.fundito.fundito.broadcast.Broadcast
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
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.cardService.getCard()
        }.onSuccess {
            emit(it)
        }
        _loading.value = false
    }

    val funditoMyMoney = liveData<Int> {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.userService.getFunditoMoney()
        }.onSuccess {
            emit(it.getOrNull(0)?.point ?: 0)
        }
        _loading.value = false
    }

    val inputMoney = MutableLiveData<Int>()

    val refundMoney = inputMoney.map {
        it * (refundPercent - 100f) * 0.01f
    }

    val totalMoney = refundMoney.map {
        it + (inputMoney.value ?: 0)
    }

    val store = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.storeInfoService.getStoreInfo(storeIdx)
        }.onSuccess {
            emit(it)
        }
        _loading.value = false
    }

    val password = MutableLiveData<String>("")

    private val _fundResult : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val fundResult : LiveData<Once<Boolean>> = _fundResult

    private val _chargeFail : MutableLiveData<Once<Unit>> = MutableLiveData()
    val chargeFail : LiveData<Once<Unit>> = _chargeFail

    fun onCheckPasswordMatch() {
        val password = password.value ?: return
        val funditoMoney = funditoMyMoney.value ?: return
        val inputMoney = inputMoney.value ?: return

        val diff = funditoMoney - inputMoney

        viewModelScope.launch {
            _loading.value = true
            kotlin.runCatching {
                if(diff < 0) {
                    try {
                        NetworkClient.userService.chargeFunditoMoney(-diff, password)
                        Broadcast.chargeCompleteEvent.send(-diff)
                    }catch(t: Throwable) {
                        _chargeFail.value = Once(Unit)
                        return@runCatching
                    }
                }

                NetworkClient.fundingService.fundWithPassword(password,storeIdx,inputMoney)
                Broadcast.fundEvent.send(storeIdx to inputMoney)
            }.onSuccess {
                _fundResult.value = Once(true)
            }.onFailure {
                _fundResult.value = Once(false)
            }
            _loading.value = false
        }
    }
}