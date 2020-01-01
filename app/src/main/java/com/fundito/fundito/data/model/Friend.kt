package com.fundito.fundito.data.model
import android.annotation.SuppressLint
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import kotlinx.android.parcel.Parcelize


/**
 * Created by mj on 01, January, 2020
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class Friend(
    @SerializedName("user_idx")
    @Expose(serialize = true, deserialize = true)
    val userIdx: Int,
    @SerializedName("id")
    @Expose(serialize = true, deserialize = true)
    val id: String,
    @SerializedName("name")
    @Expose(serialize = true, deserialize = true)
    val name: String,
    @SerializedName("nickname")
    @Expose(serialize = true, deserialize = true)
    val nickname: String
) : Parcelable