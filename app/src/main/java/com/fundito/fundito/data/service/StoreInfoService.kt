package com.fundito.fundito.data.service

import com.fundito.fundito.data.database.SearchItem
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by mj on 29, December, 2019
 */
interface StoreInfoService {

    //1
    @GET("storeInfo/{storeIdx}")
    suspend fun getStoreInfo(@Path("storeIdx") storeIdx : Int) : Store

    //6
    @GET("storeInfo/search")
    suspend fun searchStoreWithKeyword(@Query("keyword") keyword : String) : List<SearchItem>

    //7
    @GET("storefund/timeline/{storeIdx}")
    suspend fun listStoreFundingTimeLine(@Path("storeIdx") storeIdx: Int) : List<Funding>
}
