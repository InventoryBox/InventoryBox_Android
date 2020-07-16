package com.example.inventorybox.network.PUT

import com.example.inventorybox.network.POST.SomeData

data class ResponseMemo(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : HomeMemo
)

data class HomeMemo(
    val result: String
)