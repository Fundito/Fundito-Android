package com.fundito.fundito.data.model

import retrofit2.Call

interface FriendFundingRepository {

    //  정보 리스트를 받아온다.
    fun getFriendFunding(): Call<List<FriendFunding>>
}