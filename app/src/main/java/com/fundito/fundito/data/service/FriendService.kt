package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.data.model.Friend
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET


/**
 * Created by mj on 29, December, 2019
 */
interface FriendService {
    @GET("friend")
    suspend fun listFriends() : List<Friend>

    @GET("friend/fund/{friendIdx}")
    suspend fun listFriendFundings() : FriendFundingDetailResponse
}
@Parcelize
data class FriendFundingDetailResponse(
    @SerializedName("proceeding")
    @Expose(serialize = true, deserialize = true)
    val proceeding: List<Proceeding>,
//    @SerializedName("success")
//    @Expose(serialize = true, deserialize = true)
//    val success: List<Any>,
    @SerializedName("fail")
    @Expose(serialize = true, deserialize = true)
    val fail: List<Fail>
) : Parcelable {
    @Parcelize
    data class Proceeding(
        @SerializedName("storeName")
        @Expose(serialize = true, deserialize = true)
        val storeName: String,
        @SerializedName("remainingDays")
        @Expose(serialize = true, deserialize = true)
        val remainingDays: Int,
        @SerializedName("progressPercent")
        @Expose(serialize = true, deserialize = true)
        val progressPercent: Int
    ) : Parcelable

    @Parcelize
    data class Fail(
        @SerializedName("storeName")
        @Expose(serialize = true, deserialize = true)
        val storeName: String,
        @SerializedName("dueDate")
        @Expose(serialize = true, deserialize = true)
        val dueDate: String,
        @SerializedName("fundingMoney")
        @Expose(serialize = true, deserialize = true)
        val fundingMoney: Int,
        @SerializedName("refundMoney")
        @Expose(serialize = true, deserialize = true)
        val refundMoney: Int
    ) : Parcelable
}