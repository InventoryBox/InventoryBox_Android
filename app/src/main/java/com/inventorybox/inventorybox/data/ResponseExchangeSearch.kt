package com.inventorybox.inventorybox.data

data class ResponseExchangeSearch(
    val data: SomeData13,
    val message: String,
    val status: Int,
    val success: Boolean
)
data class SomeData13(
    val postInfo: List<PostInfo>
)