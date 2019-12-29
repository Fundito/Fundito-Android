package com.fundito.fundito.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fundito.fundito.data.database.SearchDao
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class SearchViewModel @Inject constructor(
    private val searchDao : SearchDao
) : ViewModel() {

    private val _items : MutableLiveData<List<String>> = MutableLiveData(listOf())
    val items : LiveData<List<String>> = _items


    val recentItems = liveData {
        this.emit(searchDao.list(50))
    }
    
    val query : MutableLiveData<String> = MutableLiveData("")


}