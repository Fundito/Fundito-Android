package com.fundito.fundito.presentation.charge

import androidx.lifecycle.*
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class ChargeViewModel @Inject constructor() : ViewModel() {

    private val _loading : MutableLiveData<Boolean> = MutableLiveData()
    val loading : LiveData<Boolean> = _loading

    val chargeMoney : MutableLiveData<Long> = MutableLiveData(0L)

    val visiblePlaceHolder = chargeMoney.map {
        it == 0L
    }

    private val _passwordMatch : MutableLiveData<Once<Boolean>> = MutableLiveData()
    val passwordMatch : LiveData<Once<Boolean>> = _passwordMatch

    val cardData = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.cardService.getCard()
        }.onSuccess {
            emit(it)
        }
        _loading.value = false
    }

    val funditoMoney = liveData {
        _loading.value = true
        kotlin.runCatching {
            NetworkClient.userService.getFunditoMoney()
        }.onSuccess {
            it.getOrNull(0)?.let { emit(it.point) }
        }
        _loading.value = false
    }


    fun onTypedPasswordComplete(password: String) {

        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.userService.chargeFunditoMoney(chargeMoney.value?.toInt() ?: 0,password)
            }.onSuccess {
                Timber.e("비밀번호 일치")
                _passwordMatch.value = Once(true)
            }.onFailure {
                Timber.e(it)
                _passwordMatch.value = Once(false)
            }
        }

    }


}