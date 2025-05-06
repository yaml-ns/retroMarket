package com.example.retromarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val _id: Int,
    val createdAt: String,
    val products: List<Product>,
    val subtotal: Double,
    val taxQC: Double,
    val taxCA: Double,
    val total: Double
) : Parcelable
