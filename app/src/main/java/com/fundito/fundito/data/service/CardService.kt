package com.fundito.fundito.data.service

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by mj on 29, December, 2019
 */
interface CardService {
    @POST("mypage/card/{userIdx}")
    @FormUrlEncoded
    suspend fun createCard(
        @Path("userIdx") userIdx : Int,
        @Field("cardCompany") cardCompany : String,
        @Field("cardNickname") cardNickname : String,
        @Field("cardNumber") cardNumber : String,
        @Field("cvc") cvc : String,
        @Field("cardPassword") cardPassword : String
    )
}