package com.fundito.fundito.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 26, December, 2019
 */
@Parcelize
data class User(
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("id")
    var id : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("nickname")
    val nickname : String?,
    @SerializedName("pay_password")
    val payPassword : String?,
    @SerializedName("point")
    val point : Int,
    @SerializedName("affectedRows")
    val affectedRows: Int,
    @SerializedName("changedRows")
    val changedRows: Int,
    @SerializedName("fieldCount")
    val fieldCount: Int,
    @SerializedName("insertId")
    val insertId: Int,
    @SerializedName("totalFundedMoney")
    val totalFundedMoney: Int,
    @SerializedName("totalGetMoney")
    val totalGetMoney: Int,
    @SerializedName("totalRewardMoney")
    val totalRewardMoney: Int,
    @SerializedName("affectedRows")
    val totalRewardPercent: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("protocol41")
    val protocol41: Boolean,
    @SerializedName("serverStatus")
    val serverStatus: Int,
    @SerializedName("warningCount")
    val warningCount: Int,
    @SerializedName("token")
    val token : String
): Parcelable

