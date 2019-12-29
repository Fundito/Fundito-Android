package com.fundito.fundito.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 29, December, 2019
 */
@Parcelize
data class Notification(
    @SerializedName("notification_idx")
    val notificationIdx: Int,
    @SerializedName("user_idx")
    val userIdx: Int,
    @SerializedName("store_idx")
    val storeIdx: Int,
    @SerializedName("type")
    val type: Int
) : Parcelable