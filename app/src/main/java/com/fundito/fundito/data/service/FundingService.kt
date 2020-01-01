package com.fundito.fundito.data.service

import android.annotation.SuppressLint
import android.os.Parcelable
import com.fundito.fundito.data.model.Funding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*


/**
 * Created by mj on 29, December, 2019
 */
interface FundingService {

    //1
    @GET("mypage/fundlist")
    suspend fun getMyFundingHistories() : List<Funding>

    //2
    @GET("funding/{storeIdx}")
    suspend fun getMaxInterestRate(@Path("storeIdx") storeIdx: Int) : MaxInterestRateResponse

    //3
    @POST("funding")
    @FormUrlEncoded
    suspend fun fundWithPassword(
        @Field("payPassword") payPassword : String,
        @Field("storeIdx") storeIdx : Int,
        @Field("fundingMoney") fundingMoney : Int
    )

    //4
    @GET("mypage/fundlist/0")
    suspend fun listCurrentFundingStore() : List<CurrentFundingResponse>


    //5
    @GET("mypage/fundlist/1")
    suspend fun listCompleteFundingStore() : List<CompleteFundingResponse>


}

@SuppressLint("ParcelCreator")
@Parcelize
data class MaxInterestRateResponse(
    @SerializedName("refundPercent")
    @Expose(serialize = true, deserialize = true)
    val refundPercent: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CurrentFundingResponse(
    @SerializedName("storeIdx")
    @Expose
    val storeIdx: Int,
    @SerializedName("storeName")
    @Expose(serialize = true, deserialize = true)
    val storeName: String,
    @SerializedName("remainingDays")
    @Expose(serialize = true, deserialize = true)
    val remainingDays: Int,
    @SerializedName("progressPercent")
    @Expose(serialize = true, deserialize = true)
    val progressPercent: Int
) : Parcelable

@SuppressLint("ParcelCreator")
@Parcelize
data class CompleteFundingResponse(
    @SerializedName("storeIdx")
    @Expose
    val storeIdx: Int,
    @SerializedName("storeName")
    @Expose(serialize = true, deserialize = true)
    val storeName: String,
    @SerializedName("dueDate")
    @Expose(serialize = true, deserialize = true)
    val dueDate: String,
    @SerializedName("fundingMoney")
    @Expose(serialize = true, deserialize = true)
    val fundingMoney: Int,
    @SerializedName("refundMoney")
    @Expose(serialize = true, deserialize = true)
    val refundMoney: Int
) : Parcelable