package com.example.inventorybox.data


data class ResponseHomeOrder(
    val data: HomeOrder
)

data class HomeOrder(
    val result: ArrayList<HomeOrderData>
)

data class HomeOrderData(
    val itemIdx: Int,
    val flag: Int,
    val itemName: String,
    val unit: String,
    val alarmCnt: Int,
    val memoCnt: Int,
    val presentCnt: Int,
    val img: String,
    val iconName: String,
    val stocksInfo: ArrayList<Int>
)