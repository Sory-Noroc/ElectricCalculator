package com.sorychan.electricost.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTest {
    fun testRetrofitImport() {
        Retrofit.Builder()
    }

    fun testCountryWebsiteConnection() {
        val url = "https://restcountries.com/v3.1/all"
        val retrofit = Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.callbackExecutor()
    }
}
