package com.fundito.fundito.data.service

import com.fundito.fundito.data.model.User
import retrofit2.http.*

/**
 * Created by mj on 29, December, 2019
 */
interface UserService {

    //1
    @PUT("auth/signuup")
    suspend fun Signup(
        @Header("access_token") facebookAccessToken : String,
        @Field("nickname") nickname: String,
        @Field("pay_password") pay_password: String
    ):User

    //2
    @GET("auth/signin")
    suspend fun Signin(
        @Header("access_token") facebookAccessToken : String
    ):User

    //3
    @GET("auth/user")
    suspend fun getListUser(

    ):User

    //5
    @PUT("mypage/point")
    @FormUrlEncoded
    suspend fun putChargeFunditoMoney(
        @Field("userIdx") userIdx: Int,
        @Field("funditoMoney") fundingMoney: Int,
        @Field("payPassword") payPassword: Int
    ): User

    //6
    @GET("mypage/fund/reward/{userIdx}")
    suspend fun getUsingFunditoMoney(
        @Path("userIdx") userIdx: Int
    ): User

    //7

    @GET("mypage/point")
    suspend fun getListFunditoMoney(

    ): User



}



