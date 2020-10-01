package com.inventorybox.inventorybox.data

data class ResponseModProfile(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ProfileResult
)

data class ProfileResult(
    val result: Boolean
)