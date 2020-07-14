package com.example.inventorybox.data

data class ResponseGraphHome(
    val data: Data
)

data class ItemInfo(
    val categoryIdx: Int,
    val iconImg: String,
    val itemIdx: Int,
    val name: String,
    val stocks: List<Int>
)

data class Data(
    val categoryInfo: List<CategoryInfo>,
    val itemInfo: List<ItemInfo>,
    val thisWeekDates: List<String>
)

data class CategoryInfo(
    val categoryIdx: Int,
    val name: String
)