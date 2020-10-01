package com.inventorybox.inventorybox.data

data class ResponseCategorySet(
    val data: SomeData9,
    val message: String,
    val status: Int,
    val success: Boolean
)


data class SomeData9(
    val categoryInfo: ArrayList<CategorySetInfo>
)


data class CategorySetInfo(
    val categoryIdx: Int,
    val name: String
)
