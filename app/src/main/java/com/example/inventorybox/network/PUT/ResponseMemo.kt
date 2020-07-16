package com.example.inventorybox.network.PUT

data class ResponseMemo(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : HomeMemo
)

data class HomeMemo(
    val result: String
)