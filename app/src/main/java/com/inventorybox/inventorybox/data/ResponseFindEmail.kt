package com.inventorybox.inventorybox.data

data class ResponseFindEmail(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Email
)

data class Email(
    val email: String
)