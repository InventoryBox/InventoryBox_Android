package com.example.inventorybox.network.POST

data class PostSignupResponse (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: String?
)