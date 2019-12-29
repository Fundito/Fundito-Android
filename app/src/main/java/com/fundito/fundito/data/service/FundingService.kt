package com.fundito.fundito.data.service

import com.fundito.fundito.data.model.Funding
import retrofit2.http.*

/**
 * Created by mj on 29, December, 2019
 */
interface FundingService {
    @GET("mypage/fundlist/{userIdx}")
    suspend fun getMyFundingHistories(@Path("userIdx") userIdx: Int) : List<Funding>

    @POST("funding")
    @FormUrlEncoded
    suspend fun fundWithPassword(
        @Field("userIdx") userIdx : Int,
        @Field("payPassword") payPassword : String,
        @Field("storeIdx") storeIdx : Int,
        @Field("fundingMoney") fundingMoney : Int
    )

    @GET("mypage/fundlist/{userIdx}")
    suspend fun listRecentFundHistories(@Path("userIdx") userIdx: Int) : List<Funding>

}