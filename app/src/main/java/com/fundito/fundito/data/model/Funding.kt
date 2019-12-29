package com.fundito.fundito.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 29, December, 2019
 */
@Parcelize
data class Funding(
    @SerializedName("funding_idx")
    val fundingIdx : Int,
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("store_idx")
    val storeIdx : Int,
    @SerializedName("funding_money")
    val fundingMoney : Int,
    @SerializedName("reward_percent")
    val rewardPercent : Int,
    @SerializedName("funding_time")
    val fundingTime : String
) : Parcelable