package com.example.retromarket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int?,
    val email:String,
    val password:String?,
    val username: String?,
    val nom: String?,
    val prenom: String?,
    val genre:String?,
    val role: String?
) : Parcelable
