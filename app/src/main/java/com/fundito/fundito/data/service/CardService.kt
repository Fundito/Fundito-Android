package com.fundito.fundito.data.service

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by mj on 29, December, 2019
 */
interface CardService {
    @POST("mypage/card")
    suspend fun createCard(
        @Body request : CardCreateRequest
    )

    @GET("mypage/card")
    suspend fun getCard() : CardResponse

    @DELETE("mypage/card")
    suspend fun deleteCard()
}

@Parcelize
data class CardCreateRequest(
    @SerializedName("cardCompany")
    val cardCompany : String,
    @SerializedName("cardNickname")
    val cardNickname : String,
    @SerializedName("cardNumber")
    val cardNumber : String,
    @SerializedName("cardExpirationDate")
    val cardExpiratioinDate : String,
    @SerializedName("cardPassword")
    val cardPassword : String
): Parcelable

@Parcelize
data class CardResponse(
    @SerializedName("userName")
    @Expose(serialize = true, deserialize = true)
    val userName: String,
    @SerializedName("cardNickname")
    @Expose(serialize = true, deserialize = true)
    var cardNickname: String,

    @SerializedName("cardNumber")
    @Expose(serialize = true, deserialize = true)
    var cardNumber: String,

    @SerializedName("cardExpirationDate")
    @Expose(serialize = true, deserialize = true)
    var cardExpirationDate: String,

    @SerializedName("cardPassword")
    @Expose(serialize = true, deserialize = true)
    var cardPassword: String

) : Parcelable