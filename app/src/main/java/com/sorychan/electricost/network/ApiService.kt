package com.sorychan.electricost.network

import com.sorychan.electricost.data.Exchange
import retrofit2.Call

interface ApiService {
    fun getExchange(): Call<Exchange>
}