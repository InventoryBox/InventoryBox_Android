package com.inventorybox.inventorybox.data

data class ResponsePostExchangeItem(
    val data: SomeData,
    val message: String,
    val status: Int,
    val success: Boolean
)


data class SomeData(
    val insertIdx: Int
)