package com.fundito.fundito.presentation.charge

import androidx.lifecycle.*
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class ChargeViewModel @Inject constructor() : ViewModel() {
    val chargeMoney : MutableLiveData<Long> = MutableLiveData(0L)

    val visiblePlaceHolder = chargeMoney.map {
        it == 0L
    }

    private val _passwordMatch : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val passwordMatch : LiveData<Once<Boolean>> = _passwordMatch


    val cardData = liveData {
        kotlin.runCatching {
            NetworkClient.cardService.getCard()
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


    fun onTypedPasswordComplete(password: String) {

        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.userService.chargeFunditoMoney(chargeMoney.value?.toInt() ?: 0,password)
            }.onSuccess {
                _passwordMatch.value = Once(true)
            }.onFailure {
                _passwordMatch.value = Once(false)
            }
        }

    }


}