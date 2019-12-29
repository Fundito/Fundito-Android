package com.fundito.fundito.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by mj on 29, December, 2019
 */
@Parcelize
data class Card(
    @SerializedName("card_idx")
    val cardIdx : Int,
    @SerializedName("user_idx")
    val userIdx : Int,
    @SerializedName("card_company_name")
    val cardCompany : Int,
    @SerializedName("card_nickname")
    val cardNickname : String,
    @SerializedName("card_number")
    val cardNumber : String,
    @SerializedName("cvc")
    val cardCvc : String,
    @SerializedName("card_password")
    val cardPassword : String
) : Parcelable