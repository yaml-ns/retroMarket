package com.example.retromarket.data.model

data class ProductResponse(
    val description:String,
    val price:Double,
    val category:String,
    val image_url:String,
    val user_id:Int
)