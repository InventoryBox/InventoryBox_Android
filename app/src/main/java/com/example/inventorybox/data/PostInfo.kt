package com.example.inventorybox.data

data class PostInfo(
    val distDiff: Int,
    val expDate: String,
    val isFood: Int,
    val latitude: Double,
    val likes: Int,
    val isSold : Int,
    val longitude: Double,
    val postIdx: Int,
    val price: Int,
    val productImg: String,
    val productName: String,
    val uploadDate: String
)