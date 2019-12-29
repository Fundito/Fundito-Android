package com.fundito.fundito.presentation.search

import androidx.lifecycle.*
import com.fundito.fundito.data.database.SearchDao
import com.fundito.fundito.data.database.SearchItem
import com.fundito.fundito.data.model.Store
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 28, December, 2019
 */
class SearchViewModel @Inject constructor(
    private val searchDao : SearchDao
) : ViewModel() {

    private val _items : MutableLiveData<List<Store>> = MutableLiveData(listOf())
    val items : LiveData<List<Store>> = _items


    val recentItems = liveData {
        this.emitSource(searchDao.list(50))
    }
    
    val query : MutableLiveData<String> = MutableLiveData("")


    fun onQueryChanged() {
        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.storeInfoService.searchStoreWithKeyword(query.value!!)
            }.onSuccess {
                _items.value = it.mapNotNull {
                    try{NetworkClient.storeInfoService.getStoreInfo(it.storeIdx)}catch(t : Throwable){null}
                }
            }.onFailure {
                Timber.e(it)
            }
        }

    }

    fun onItemDeleted(item : SearchItem) = viewModelScope.launch{
        kotlin.runCatching {
            searchDao.delete(item)
        }.onSuccess {

        }.onFailure {

        }

    }

}