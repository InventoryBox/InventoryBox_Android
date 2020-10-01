package com.inventorybox.inventorybox.data

data class ResponseGraphHome(
    val data: Data
//    val categoryInfo: List<CategoryInfo>,
//    val itemInfo: List<ItemInfo>,
//    val thisWeekDates: List<String>
)

data class ItemInfo(
    val categoryIdx: Int,
    val iconImg: String,
    val itemIdx: Int,
    val name: String,
    val stocks: ArrayList<Int>,
    val alarmCnt: Int
)

data class Data(
    val categoryInfo: List<CategoryInfo>,
    val itemInfo: ArrayList<ItemInfo>,
    val thisWeekDates: ArrayList<String>
)

data class CategoryInfo(
    val categoryIdx: Int,
    val name: String
)