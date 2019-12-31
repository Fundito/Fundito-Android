package com.fundito.fundito.presentation.main.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.common.widget.Once
import com.fundito.fundito.data.service.NetworkClient
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


    private val _onGoingFundingItems : MutableLiveData<List<String>> = MutableLiveData(listOf("","","",""))
    val onGoingFundingItems : LiveData<List<String>> = _onGoingFundingItems

    private val _completeFundingItem : MutableLiveData<List<String>> = MutableLiveData(listOf("","","",""))
    val completeFundingItem : LiveData<List<String>> = _completeFundingItem

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
}