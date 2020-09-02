package com.example.inventorybox.data

data class ResponsePersonalData(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: Personal
)

data class Personal(
    val result: ArrayList<DataPersonal>
)

data class DataPersonal(
    val repName: String,
    val coName: String,
    val phoneNumber: String,
    val location: String
)