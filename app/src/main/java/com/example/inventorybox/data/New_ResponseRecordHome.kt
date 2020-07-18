package com.example.inventorybox.data

data class New_ResponseRecordHome(
    val data: Somedata11,
    val message: String,
    val status: Int,
    val success: Boolean
)


data class Somedata11(
    val addButton: Int,
    val categoryInfo: List<new_CategoryInfo>,
    val isRecorded: Int,
    val itemInfo: List<new_ItemInfo>,
    val picker: Int
)


data class new_CategoryInfo(
    val categoryIdx: Int,
    val name: String
)


data class new_ItemInfo(
    val alarmCnt: Int,
    val categoryIdx: Int,
    val img: String,
    val itemIdx: Int,
    val name: String,
    val stocksCnt: Int,
    val unit: String
)