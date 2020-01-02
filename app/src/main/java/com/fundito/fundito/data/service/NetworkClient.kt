package com.fundito.fundito.data.service

import android.os.Parcelable
import com.fundito.fundito.common.util.SPUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
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
import timber.log.Timber

/**
 * Created by mj on 22, December, 2019
 *
 * @author MJ
 */
object NetworkClient {

    private const val BASE_URL = "http://15.164.37.160:3000"

    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val commonNetworkInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {

            /**
             * 1) Common Header with API Access Token
             */
            val newRequest = chain.request().newBuilder()
                .addHeader("token", SPUtil.accessToken).build()

//            Timber.e("TOKEN : ${SPUtil.accessToken}")
            Timber.e("${newRequest.method} - ${newRequest.url} \n{\n\t${newRequest.body?.toString()}\n}\n" )

            /**
             * 2) General Response from Server (Unwrapping data)
             */
            val response = chain.proceed(newRequest)

            /**
             * 3) Parse body to json
             */
            val rawJson = response.body?.string() ?: "{}"

            Timber.e(rawJson)

            /**
             * 4) Wrap body with gson
             */
            val type = object : TypeToken<ResponseWrapper<*>>() {}.type
            val res = try {
                val r = gson.fromJson<ResponseWrapper<*>>(rawJson, type) ?: throw JsonSyntaxException("Parse Fail")

                if(!r.success)
                    ResponseWrapper<Any>(-999, false, "Server Logic Fail : ${r.message}", null)
                else
                    r

            } catch (e: JsonSyntaxException) {
                ResponseWrapper<Any>(-999, false, "json parsing fail : $e", null)
            } catch (t: Throwable) {
                ResponseWrapper<Any>(-999, false, "unknown error : $t", null)
            }


            /**
             * 5) get data json from data
             */
            val dataJson = gson.toJson(res.data)

            /**
             * 6) return unwrapped response with body
             */
            return response.newBuilder()
                .message(res.message)
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
    val cardService: CardService = retrofit.create(CardService::class.java)
    val friendService: FriendService = retrofit.create(FriendService::class.java)
    val fundingService: FundingService = retrofit.create(FundingService::class.java)
    val notificationService: NotificationService = retrofit.create(NotificationService::class.java)
    val storeFundService: StoreFundService = retrofit.create(StoreFundService::class.java)
    val storeInfoService: StoreInfoService = retrofit.create(StoreInfoService::class.java)
    val userService: UserService = retrofit.create(UserService::class.java)

}

/**
 * For Response Form Unwrapping
 */
@Parcelize
data class ResponseWrapper<T>(
    @SerializedName("status")
    val status: Int,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: @RawValue T? = null
) : Parcelable
