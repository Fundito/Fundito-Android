package com.fundito.fundito.presentation.search

import androidx.lifecycle.*
import com.fundito.fundito.data.database.SearchDao
import com.fundito.fundito.data.database.SearchItem
import kotlinx.coroutines.launch
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
        this.emitSource(searchDao.list(50))
    }
    
    val query : MutableLiveData<String> = MutableLiveData("")


    fun onItemDeleted(item : SearchItem) = viewModelScope.launch{
        kotlin.runCatching {
            searchDao.delete(item)
        }.onSuccess {

        }.onFailure {

        }

    }

}