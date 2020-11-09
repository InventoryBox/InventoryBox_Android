package com.inventorybox.inventorybox.data

data class ResponseRecordHome(
    val data: RecordHomeData
)

data class RecordHomeData(
    val addButton: Int,
    val categoryInfo: List<RecordHomeCategoryInfo>,
    val date: String,
    val isRecorded: Int,
    val itemInfo: List<RecordHomeItemInfo>,
    val picker: Int
)

data class RecordHomeItemInfo(
    val alarmCnt: Int,
    val categoryIdx: Int,
    val img: String,
    val itemIdx: Int,
    val name: String,
    val stocksCnt: Int,
    val unit: String,
    var isSelected : Boolean
)

data class RecordHomeCategoryInfo(
    val categoryIdx: Int,
    val name: String
)