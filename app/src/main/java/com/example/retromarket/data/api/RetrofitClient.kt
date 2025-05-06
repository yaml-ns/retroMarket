package com.example.retromarket.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
//    private const val BASE_URL = "http://10.0.2.2:3001/api/"
//    private const val BASE_URL = "http://192.168.2.17:3001/api/"
    private const val BASE_URL = "http://192.168.203.114:3001/api/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}