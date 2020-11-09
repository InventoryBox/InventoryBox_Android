package com.inventorybox.inventorybox.data

data class RequestSignup (
    val email: String,
    val password: String,
    val nickName: String,
    val repName: String,
    val coName: String,
    val img: String,
    val phoneNumber: Int,
    val isAccepted: Int
)