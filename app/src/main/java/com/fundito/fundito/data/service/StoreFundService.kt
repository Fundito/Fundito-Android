package com.fundito.fundito.data.service

import com.fundito.fundito.data.model.StoreFunding
import retrofit2.http.*

/**
 * Created by mj on 29, December, 2019
 */
interface StoreFundService {
    @POST("storefund/{storeIdx}")
    @FormUrlEncoded
    fun registerStoreFunding(
        @Path("storeIdx") storeIdx : Int,
        @Field("marginPercent") marginPercent : Int,
        @Field("regularMoney") regularMoney : Int,
        @Field("goalMoney") goalMoney : Int
    )

    @POST("storefund/{storeIdx}")
    fun getStoreFunding(
        @Path("storeIdx") storeIdx : Int
    ) : List<StoreFunding>

    @POST("storefund")
    fun listStoreFunding() : List<StoreFunding>

    @DELETE("storefund/{storeIdx")
    fun deleteStoreFunding(
        @Path("storeIdx") storeIdx: Int
    )

    @PUT("storefund/{storeIdx}")
    fun updateStoreFunding(
        @Path("storeIdx") storeIdx : Int,
        @Field("customerCount") customerCount: Int,
        @Field("marginPercent") marginPercent : Int,
        @Field("goalMoney") goalMoney: Int
    )
}