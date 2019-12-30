package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.data.model.Funding
import com.fundito.fundito.data.model.Store
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
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
    @GET("storefund/search")
    suspend fun searchStoreWithKeyword(@Query("keyword") keyword : String) : List<SearchResponseItem>

    //7
    @GET("storefund/timeline/{storeIdx}")

    suspend fun listStoreFundingTimeLine(@Path("storeIdx") storeIdx: Int) : List<Funding>


}

@Parcelize
data class SearchResponseItem(
    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    val name: String,
    @SerializedName("thumbnail")
    @Expose(serialize = true, deserialize = true)
    val thumbnail: String,
    @SerializedName("storefund_idx")
    @Expose(serialize = true, deserialize = true)
    val storefundIdx: Int,
    @SerializedName("store_idx")
    @Expose(serialize = true, deserialize = true)
    val storeIdx: Int,
    @SerializedName("margin_percent")
    @Expose(serialize = true, deserialize = true)
    val marginPercent: Double,
    @SerializedName("register_time")
    @Expose(serialize = true, deserialize = true)
    val registerTime: String,
    @SerializedName("due_date")
    @Expose(serialize = true, deserialize = true)
    val dueDate: String,
    @SerializedName("regular_money")
    @Expose(serialize = true, deserialize = true)
    val regularMoney: Int,
    @SerializedName("goal_money")
    @Expose(serialize = true, deserialize = true)
    val goalMoney: Int,
    @SerializedName("current_sales")
    @Expose(serialize = true, deserialize = true)
    val currentSales: Int,
    @SerializedName("contributer_count")
    @Expose(serialize = true, deserialize = true)
    val contributerCount: Int,
    @SerializedName("fund_status")
    @Expose(serialize = true, deserialize = true)
    val fundStatus: Int,
    @SerializedName("currentGaolPercent")
    @Expose(serialize = true, deserialize = true)
    val currentGaolPercent: Int,
    @SerializedName("address")
    @Expose
    val address : String
) : Parcelable
