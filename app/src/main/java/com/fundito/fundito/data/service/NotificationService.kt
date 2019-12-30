package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.data.model.Store
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET


/**
 * Created by mj on 29, December, 2019
 */
interface NotificationService {
    @GET("notification")
    suspend fun listNotis() : List<NotificationResponse>

    @GET("notification")
    suspend fun listUserNotis() //TODO
}

@Parcelize
data class NotificationResponse(
    @SerializedName("notification_idx")
    @Expose(serialize = true, deserialize = true)
    val notificationIdx: Int,
    @SerializedName("user_idx")
    @Expose(serialize = true, deserialize = true)
    val userIdx: Int,
    @SerializedName("store_idx")
    @Expose(serialize = true, deserialize = true)
    val storeIdx: Int,
    @SerializedName("date")
    @Expose(serialize = true, deserialize = true)
    val date: String,
    @SerializedName("store_info")
    @Expose(serialize = true, deserialize = true)
    val storeInfo: Store
) : Parcelable