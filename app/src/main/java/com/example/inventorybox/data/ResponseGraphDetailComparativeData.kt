package com.example.inventorybox.data

data class ResponseGraphDetailComparativeData(
    val data: GraphDoubleData,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class GraphDoubleData(
    val week1: ArrayList<Int>,
    val week2: ArrayList<Int>
)
