package com.example.retromarket.data.model

data class CartResponse(
    val success: Boolean,
    val message: String,
    val cart: List<CartItem>?
)