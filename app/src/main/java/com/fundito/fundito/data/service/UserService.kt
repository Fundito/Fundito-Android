package com.fundito.fundito.data.service

import android.annotation.SuppressLint
import android.os.Parcelable
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
    suspend fun signUp(
        @Header("access_token") facebookAccessToken : String,
        @Field("nickname") nickname: String,
        @Field("pay_password") pay_password: String
    ):User

    //2
    @GET("auth/signin")
    suspend fun signIn(
        @Header("access_token") facebookAccessToken : String
    ):TokenResponse

    //3
    @GET("auth/user")
    suspend fun getUser():UserResponse

    //5
    @PUT("mypage/point")
    @FormUrlEncoded
    suspend fun putChargeFunditoMoney(
        @Field("funditoMoney") fundingMoney: Int,
        @Field("payPassword") payPassword: Int
    )

    //6
    @GET("mypage/fund/reward")
    suspend fun getUsingFunditoMoney(): CurrentFundingStatus

    //7

    @GET("mypage/point")
    suspend fun getFunditoMoney(): List<FunditoMoneyResponse>



}
@SuppressLint("ParcelCreator")
@Parcelize
data class TokenResponse(
    @SerializedName("token")
    @Expose(serialize = true, deserialize = true)
    val token: String
) : Parcelable

@Parcelize
data class UserResponse(
    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    val name: String,
    @SerializedName("nickname")
    @Expose(serialize = true, deserialize = true)
    val nickname: String,
    @SerializedName("point")
    @Expose(serialize = true, deserialize = true)
    val point: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CurrentFundingStatus(
    @SerializedName("totalGetMoney")
    @Expose(serialize = true, deserialize = true)
    val totalGetMoney: Int,
    @SerializedName("totalFundedMoney")
    @Expose(serialize = true, deserialize = true)
    val totalFundedMoney: Int,
    @SerializedName("totalRewardMoney")
    @Expose(serialize = true, deserialize = true)
    val totalRewardMoney: Int,
    @SerializedName("totalRewardPercent")
    @Expose(serialize = true, deserialize = true)
    val totalRewardPercent: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class FunditoMoneyResponse(
    @SerializedName("point")
    @Expose(serialize = true, deserialize = true)
    val point: Int
) : Parcelable


