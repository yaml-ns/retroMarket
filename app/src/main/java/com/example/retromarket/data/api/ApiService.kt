package com.example.retromarket.data.api

import com.example.retromarket.data.model.AuthResponse
import com.example.retromarket.data.model.Product
import com.example.retromarket.data.model.ProductResponse
import com.example.retromarket.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun login(@Body user: User): Call<AuthResponse>
    @POST("register")
    fun register(@Body user: User): Call<AuthResponse>

    @POST("forgot-password")
    fun forgotPassword(@Body email: Map<String, String>): Call<AuthResponse>

    @GET("products")
    fun getAllProducts(): Call<List<Product>>
}
