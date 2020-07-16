package com.example.inventorybox.data

data class RequestPostExchangeItem(
    val PostItemInfo: PostItemInfo
)


data class PostItemInfo(
    val coverPrice: Int,
    val description: String,
    val expDate: String?,
    val isFood: Int,
    val price: Int,
    val productName: String,
    val quantity: Int,
    val unit: String
)