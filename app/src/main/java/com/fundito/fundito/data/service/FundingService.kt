package com.fundito.fundito.data.service

import android.annotation.SuppressLint
import android.os.Parcelable
import com.fundito.fundito.data.model.Funding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by mj on 29, December, 2019
 */
interface FundingService {

    //1
    @GET("mypage/fundlist")
    suspend fun getMyFundingHistories() : List<Funding>

    //2
    @GET("funding/{storeIdx}")
    suspend fun getMaxInterestRate() : MaxInterestRateResponse

    //3
    @POST("funding")
    @FormUrlEncoded
    suspend fun fundWithPassword(
        @Field("payPassword") payPassword : String,
        @Field("storeIdx") storeIdx : Int,
        @Field("fundingMoney") fundingMoney : Int
    )


}

@SuppressLint("ParcelCreator")
@Parcelize
data class MaxInterestRateResponse(
    @SerializedName("refundPercent")
    @Expose(serialize = true, deserialize = true)
    val refundPercent: Int
) : Parcelable