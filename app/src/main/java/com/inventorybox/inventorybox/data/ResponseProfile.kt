package com.inventorybox.inventorybox.data

data class ResponseProfile(
    val status: Int,
    val success: Boolean,
    val data: SomeData14
)

data class SomeData14(
    val nickname: String,
    val img: String,
    val coName: String
)