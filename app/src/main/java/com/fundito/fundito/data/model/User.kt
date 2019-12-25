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
    val name : String
) : Parcelable