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
    val point : Int
) : Parcelable