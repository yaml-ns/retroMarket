package com.example.retromarket.data.model

data class CartItem(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val img: String
)