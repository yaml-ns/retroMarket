package com.example.retromarket.data.model

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserInfo
)
