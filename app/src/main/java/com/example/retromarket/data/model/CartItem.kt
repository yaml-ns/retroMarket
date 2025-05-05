package com.example.retromarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val id: Int,
    val title: String,
    val price: Double,
    val quantity: Int,
    val img: String
) : Parcelable