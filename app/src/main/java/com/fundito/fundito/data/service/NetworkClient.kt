package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.common.util.SPUtil
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by mj on 22, December, 2019
 */
object NetworkClient {

    private const val BASE_URL = "http://15.165.109.29:8080"

    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val commonNetworkInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("token",SPUtil.accessToken).build()
            val response = chain.proceed(newRequest)
            val json = response.body?.string() ?: """
                {
                    "status": -999,
                    "success": false,
                    "message": json parsing fail"
                }
                """.trimIndent()

            val type = object : TypeToken<ResponseWrapper<*>>(){}.type

            val res = gson.fromJson<ResponseWrapper<*>>(json,type)

            if(!res.success)
                return response

            val dataJson = gson.toJson(res.data!!)

            return response.newBuilder()
                .body(dataJson.toResponseBody())
                .build()
        }
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(commonNetworkInterceptor)
        .build()

    private val gson = GsonBuilder()
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Services
     */
    val cardService : CardService = retrofit.create(CardService::class.java)
    val friendService : FriendService = retrofit.create(FriendService::class.java)
    val fundingService : FundingService = retrofit.create(FundingService::class.java)
    val notificationService : NotificationService = retrofit.create(NotificationService::class.java)
    val storeFundService : StoreFundService = retrofit.create(StoreFundService::class.java)
    val storeInfoService : StoreInfoService = retrofit.create(StoreInfoService::class.java)
    val userService : UserService = retrofit.create(UserService::class.java)

}

/**
 * For Response Form Unwrapping
 */
@Parcelize
data class ResponseWrapper<T>(
    @SerializedName("status")
    val status : Int,
    @SerializedName("success")
    val success : Boolean,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val data : @RawValue T?
) : Parcelable

//fun <T> ResponseWrapper<T>.unwrap() : T {
//    return data!!
//}