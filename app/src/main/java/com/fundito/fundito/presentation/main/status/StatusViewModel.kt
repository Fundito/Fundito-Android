package com.fundito.fundito.presentation.main.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class StatusViewModel @Inject constructor() : ViewModel() {

    val sheetOpenCount : MutableLiveData<Int> = MutableLiveData(0)

    val sheet2TabIndex : MutableLiveData<Int> = MutableLiveData(0)

    private val _recentFundingHistories : MutableLiveData<List<String>> = MutableLiveData(listOf("","","",""))
    val recentFundingHistories : LiveData<List<String>> = _recentFundingHistories
    
    private val _onGoingFundingItems : MutableLiveData<List<String>> = MutableLiveData(listOf("","","",""))
    val onGoingFundingItems : LiveData<List<String>> = _onGoingFundingItems

    private val _completeFundingItem : MutableLiveData<List<String>> = MutableLiveData(listOf("","","",""))
    val completeFundingItem : LiveData<List<String>> = _completeFundingItem

    
}