package com.inventorybox.inventorybox.network.POST

data class ResponseEmail(
    val status : Int,
    val success : Boolean,
    val message : String,
    var data: EmailNumber
)

data class EmailNumber(
    var number: Int
)