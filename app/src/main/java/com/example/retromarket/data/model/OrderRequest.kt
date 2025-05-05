package com.example.retromarket.data.model


data class OrderRequest(
    val userId: Int,
    val products: List<CartItem>,
    val shipping: Shipping,
    val payment: Payment
)