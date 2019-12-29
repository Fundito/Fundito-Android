package com.fundito.fundito.data.service

import com.fundito.fundito.data.model.Funding
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by mj on 29, December, 2019
 */
interface FundingService {
    @GET("mypage/fundlist/{userIdx}")
    suspend fun getMyFundingHistories(@Path("userIdx") userIdx : Int) : List<Funding>
}