package com.example.inventorybox.data

data class ResponseProfile(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: SomeData14
)

data class SomeData14(
    val nickname: String,
    val img: String
)