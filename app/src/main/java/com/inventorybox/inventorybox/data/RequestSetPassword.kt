package com.inventorybox.inventorybox.data

data class RequestSetPassword(
        val email : String,
        val updatedPassword: String
)