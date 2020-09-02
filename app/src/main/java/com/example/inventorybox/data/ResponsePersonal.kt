package com.example.inventorybox.data

data class ResponsePersonal(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: PersonalData
)

data class PersonalData(
    val result: Boolean
)