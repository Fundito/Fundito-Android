package com.fundito.fundito.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fundito.fundito.data.service.NetworkClient.fundingService
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 27, December, 2019
 */
class MainViewModel @Inject constructor()  : ViewModel() {


    init {
        viewModelScope.launch {

            runCatching {
                fundingService.getMyFundingHistories(1)
            }.onSuccess {
                Timber.e(it.toString())
            }.onFailure {
                Timber.e(it)
            }

        }

    }

}
