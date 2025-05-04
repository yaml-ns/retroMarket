package com.example.retromarket.data.model

data class AuthResponse(
    val token: String?,
    val message: String,
    val user: User
)