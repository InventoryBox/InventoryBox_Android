package com.inventorybox.inventorybox.data

data class RequestRecordDelete(
    val itemInfo: MutableList<CategoryMove>
)
data class CategoryMove(
    val itemIdx : Int,
    val categoryIdx:Int
)