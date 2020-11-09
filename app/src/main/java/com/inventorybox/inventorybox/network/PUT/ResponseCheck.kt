package com.inventorybox.inventorybox.network.PUT


data class ResponseHomeCheck(
    val status: Int,
    val success: Boolean,
    val message: String
)