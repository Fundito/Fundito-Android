package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.data.model.Friend
import com.fundito.fundito.data.model.Funding
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by mj on 29, December, 2019
 */
interface FriendService {
    @GET("friend")
    suspend fun listFriends() : List<Friend>

    @GET("friend/fund/{friendIdx}")
    suspend fun listFriendFundings(
        @Path("friendIdx") friendIdx: Int
    ) : FriendFundingDetailResponse

    @GET("friend/fund")
    suspend fun monthlyDitoList() : List<MonthlyDitoResponse>

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

@Parcelize
data class MonthlyDitoResponse(
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
    val nickname: String,
    @SerializedName("fund")
    @Expose(serialize = true, deserialize = true)
    val fund: List<Funding>
) : Parcelable