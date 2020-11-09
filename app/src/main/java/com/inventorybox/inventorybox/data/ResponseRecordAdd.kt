package com.inventorybox.inventorybox.data

data class ResponseRecordAdd(
    val data: RecordAddData,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class RecordAddData(
    val categoryInfo: List<RecordAddCategoryInfo>,
    val iconInfo: List<RecordAddIconInfo>
)

data class RecordAddIconInfo(
    val iconIdx: Int,
    val img: String,
    val name: String
)

data class RecordAddCategoryInfo(
    val categoryIdx: Int,
    val name: String
)

