package com.example.inventorybox.network.POST

data class ResponseLogin(
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : SomeData
)

//test
data class SomeData(
    val token : String
)