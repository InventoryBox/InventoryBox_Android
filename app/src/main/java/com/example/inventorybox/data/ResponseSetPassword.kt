package com.example.inventorybox.data

data class ResponseSetPassword(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Password
)

data class Password(
    val result: Boolean
)