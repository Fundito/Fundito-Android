package com.fundito.fundito.data.model

import android.os.Parcelable
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
    @SerializedName("tel_number")
    val telNumber : String,
    @SerializedName("location_latitude")
    val latitude : String,
    @SerializedName("location_longitude")
    val longitude : String,
    @SerializedName("address")
    val address : String,
    @SerializedName("business_hour")
    val businessHour : String,
    @SerializedName("breaktime")
    val breakTime : String,
    @SerializedName("holiday")
    val holiday : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("wifi_SSID")
    val wifiSSID : String
) : Parcelable {
    @Parcelize
    data class Menu(
        @SerializedName("menu_name")
        val name : String,
        @SerializedName("menu_price")
        val price : String
    ) : Parcelable
}