package com.example.retromarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id:Int,
    val description:String,
    val price:Double,
    val category:String,
    val image:String,
    val user_id:Int
) : Parcelable
