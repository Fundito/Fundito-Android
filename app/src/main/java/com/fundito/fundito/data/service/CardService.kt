package com.fundito.fundito.data.service

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import retrofit2.http.*


/**
 * Created by mj on 29, December, 2019
 */
interface CardService {
    @POST("mypage/card/{userIdx}")
    @FormUrlEncoded
    suspend fun createCard(
        @Path("userIdx") userIdx : Int,
        @Field("cardCompany") cardCompany : String,
        @Field("cardNickname") cardNickname : String,
        @Field("cardNumber") cardNumber : String,
        @Field("cvc") cvc : String,
        @Field("cardPassword") cardPassword : String
    )

    @GET("mypage/card")
    suspend fun getCard() : CardResponse

    @DELETE("mypage/card")
    suspend fun deleteCard()
}
@Parcelize
data class CardResponse(
    @SerializedName("userName")
    @Expose(serialize = true, deserialize = true)
    val userName: String,
    @SerializedName("cardNickname")
    @Expose(serialize = true, deserialize = true)
    val cardNickname: String
) : Parcelable