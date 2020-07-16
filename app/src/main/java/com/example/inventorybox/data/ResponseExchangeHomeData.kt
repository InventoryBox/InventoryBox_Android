package com.example.inventorybox.data

data class ResponseExchangeHomeData(
    val data: SomeData1,
    val status: Int,
    val success: Boolean
)

data class SomeData1(
    val postInfo: ArrayList<PostInfo>,
    val addressInfo : String
)