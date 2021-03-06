package com.fundito.fundito.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import com.fundito.fundito.data.enumerator.FundStatus
import com.fundito.fundito.data.enumerator.FundStatusSerializer
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 26, December, 2019
 */
@Parcelize
data class Store(
    @SerializedName("store_idx")
    val storeIdx : Int,
    @SerializedName("name")
    var name : String,
    @SerializedName("address")
    val address : String,
    @SerializedName("business_hour")
    val businessHour : String,
    @SerializedName("breaktime")
    val breakTime : String,
    @SerializedName("holiday")
    val holiday : String,
    @SerializedName("thumbnail")
    val thumbnail : String?,
    @SerializedName("menu")
    val menus : List<Menu>,
    @SerializedName("goal_sales")
    val goalMoney : Int,
    @SerializedName("current_goal_percent")
    val currentGoalPercent : Int,
    @SerializedName("refund_percent")
    val refundPercent : Int,
    @SerializedName("refund_percent_of_percent")
    val refundPercentOfPercent : Int,
    @SerializedName("left_day")
    val leftDay: Int,
    @SerializedName("due_date")
    val dueDate : String,
    @SerializedName("fund_status")
    @JsonAdapter(FundStatusSerializer::class)
    val fundStatus : FundStatus,

    @SerializedName("funding")
    val funding: FundingInStore?

) : Parcelable {
    @Parcelize
    data class Menu(
        @SerializedName("menu_name")
        val name : String,
        @SerializedName("menu_price")
        val price : String
    ) : Parcelable

}

@SuppressLint("ParcelCreator")
@Parcelize
data class FundingInStore(
    @SerializedName("funding_idx")
    @Expose(serialize = true, deserialize = true)
    val fundingIdx: Int,
    @SerializedName("user_idx")
    @Expose(serialize = true, deserialize = true)
    val userIdx: Int,
    @SerializedName("store_idx")
    @Expose(serialize = true, deserialize = true)
    val storeIdx: Int,
    @SerializedName("funding_money")
    @Expose(serialize = true, deserialize = true)
    val fundingMoney: Int,
    @SerializedName("refund_percent")
    @Expose(serialize = true, deserialize = true)
    val refundPercent: Int,
    @SerializedName("reward_money")
    @Expose(serialize = true, deserialize = true)
    val rewardMoney: Int,
    @SerializedName("profit_money")
    @Expose(serialize = true, deserialize = true)
    val profitMoney: Int,
    @SerializedName("funding_time")
    @Expose(serialize = true, deserialize = true)
    val fundingTime: String,
    @SerializedName("is_withdraw")
    @Expose(serialize = true, deserialize = true)
    val isWithdraw: Int? = -1,
    @SerializedName("user_name")
    @Expose(serialize = true, deserialize = true)
    val userName: String
) : Parcelable