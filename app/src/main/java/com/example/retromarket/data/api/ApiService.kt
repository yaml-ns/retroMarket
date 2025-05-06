package com.example.retromarket.data.api

import com.example.retromarket.data.model.AuthResponse
import com.example.retromarket.data.model.CartItem
import com.example.retromarket.data.model.CartResponse
import com.example.retromarket.data.model.Order
import com.example.retromarket.data.model.OrderRequest
import com.example.retromarket.data.model.OrderResponse
import com.example.retromarket.data.model.Product
import com.example.retromarket.data.model.ProductResponse
import com.example.retromarket.data.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body user: User): Call<AuthResponse>
    @POST("register")
    fun register(@Body user: User): Call<AuthResponse>

    @POST("forgot-password")
    fun forgotPassword(@Body email: Map<String, String>): Call<AuthResponse>

    @GET("products")
    fun getAllProducts(): Call<List<Product>>


    @POST("cart/{id}")
    fun addToCart(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<CartResponse>

    @GET("cart")
    fun getCart(
        @Header("Authorization") token: String
    ): Call<List<CartItem>>

    @DELETE("cart/{id}")
    fun removeFromCart(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<CartResponse>

    @GET("cart-count")
    fun getCartCount(
        @Header("Authorization") token: String
    ): Call<Map<String, Int>> // ou un modèle selon la réponse

    @POST("cart/checkout")
    fun checkoutCart(
        @Header("Authorization") token: String
    ): Call<CartResponse>

    @DELETE("cart/clear")
    fun clearCart(
        @Header("Authorization") token: String
    ): Call<CartResponse>

    @POST("orders")
    fun createOrder(
        @Header("Authorization") token: String,
        @Body orderRequest: OrderRequest
    ): Call<OrderResponse>

    @POST("products")
    fun createProduct(
        @Header("Authorization") token: String,
        @Body product: Product
    ): Call<ProductResponse>

    @GET("my-products")
    fun getMyProducts(
        @Header("Authorization") token: String
    ): Call<List<Product>>

    @PUT("products/{id}")
    fun updateProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int,
        @Body product: Product
    ): Call<ProductResponse>

    @DELETE("products/{id}")
    fun deleteProduct(
        @Header("Authorization") token: String,
        @Path("id") productId: Int
    ): Call<Void>

    @GET("orders")
    fun getUserOrders(
        @Header("Authorization") token: String
    ): Call<List<Order>>

    @GET("orders/{id}")
    fun getOrderById(
        @Header("Authorization") token: String,
        @Path("id") orderId: Int
    ): Call<Order>
}
