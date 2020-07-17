package com.example.inventorybox.data

data class ResponseRecordRecord(
    val data: RecordRecordData,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class RecordRecordCategoryInfo(
    val categoryIdx: Int,
    val name: String
)

data class RecordRecordData(
    val categoryInfo: List<RecordRecordCategoryInfo>,
    val date: String,
    val itemInfo: List<RecordRecordItemInfo>
)

data class RecordRecordItemInfo(
    val categoryIdx: Int,
    val img: String,
    val itemIdx: Int,
    val name: String
)