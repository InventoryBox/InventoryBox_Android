package com.example.inventorybox.data

data class ResponseExchangeMyPost(
    val data: SomeData12,
    val message: String,
    val status: Int,
    val success: Boolean
)
data class SomeData12(
    val itemInfo: List<PostInfo>
)