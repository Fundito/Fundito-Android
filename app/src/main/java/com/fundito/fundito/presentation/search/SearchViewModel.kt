package com.fundito.fundito.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _items : MutableLiveData<List<String>> = MutableLiveData(listOf("","","","","","","","","",""))
    val items : LiveData<List<String>> = _items
}