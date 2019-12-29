package com.fundito.fundito.data.service

import com.fundito.fundito.data.model.Store
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by mj on 29, December, 2019
 */
interface StoreInfoService {

    @GET("storeInfo/{storeIdx}")
    suspend fun getStoreInfo(@Path("storeIdx") storeIdx : Int) : Store
}