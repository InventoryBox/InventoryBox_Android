package com.inventorybox.inventorybox.data

data class GraphSingleData(
    val categoryIdx: Int,
    val iconImg: String,
    val itemIdx: Int,
    val name: String,
    val stocks: List<Int>
)