package com.fundito.fundito.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 29, December, 2019
 */
@Parcelize
data class StoreFunding(
    @SerializedName("storefund_idx")
    val storeFundIdx : Int,
    @SerializedName("store_idx")
    val storeIdx : Int,
    @SerializedName("margin_ppercent")
    val marginPercent : Double,
    @SerializedName("register_time")
    val registerTime : String,
    @SerializedName("due_date")
    val dueDate : String,
    @SerializedName("regular_money")
    val regularMoney : Int,
    @SerializedName("goal_money")
    val goalMoney : Int,
    @SerializedName("current_sales")
    val currentSales : Int,
    @SerializedName("contributer_count")
    val contributorCount : Int,
    @SerializedName("fund_status")
    val fundStatus : Int
): Parcelable