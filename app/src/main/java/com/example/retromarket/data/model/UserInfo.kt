package com.example.retromarket.data.model

data class UserInfo(
    val id: Int,
    val username: String,
    val nom: String?,
    val prenom: String?,
    val role: String?
)