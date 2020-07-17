package com.example.inventorybox.data

data class ResponseCategoryAdd(
    val data: CategoryAddData,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class CategoryAddData(
    val insertId: Int
)