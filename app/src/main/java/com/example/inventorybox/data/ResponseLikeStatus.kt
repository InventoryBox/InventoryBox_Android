package com.example.inventorybox.data

data class ResponseLikeStatus(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : SomeData17
)

data class SomeData17(
    val likes : Int
)