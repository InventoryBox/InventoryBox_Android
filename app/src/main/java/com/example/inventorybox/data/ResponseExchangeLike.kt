package com.example.inventorybox.data

data class ResponseExchangeLike(
    val data: SomeData10,
    val status: Int,
    val success: Boolean
)
data class SomeData10(
    val postInfo: MutableList<PostInfo>
)