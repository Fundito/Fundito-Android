package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*

/**
 * Created by mj on 29, December, 2019
 */
interface UserService {

    //1
    @PUT("auth/signuup")
    suspend fun Signup(
        @Field("nickname") nickname : String,
        @Field("pay_password") pay_password : String
    )

    //2
    @GET("auth/signin")
    suspend fun Signin(

    )

    //5
    @PUT("mypage/point")
    @FormUrlEncoded
    suspend fun putChargeFunditoMoney(
        @Field("userIdx") userIdx : Int,
        @Field("funditoMoney") fundingMoney: Int,
        @Field("payPassword") payPassword: Int
    ) : List<User>

    //6
    @GET("mypage/fund/reward/{userIdx}")
    suspend fun getUsingFunditoMoney(
        @Path("userIdx") userIdx: Int
    ) : List<User>

    //7
    @GET("mypage/point")
    suspend fun getListFunditoMoney(
    )

}

