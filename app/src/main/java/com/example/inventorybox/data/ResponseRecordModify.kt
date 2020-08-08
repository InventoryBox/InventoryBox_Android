package com.example.inventorybox.data

data class ResponseRecordModify(
    val data: RecordModifyData,
    val message: String,
    val status: Int,
    val success: Boolean
)
//
//data class RecordModifyCategoryInfo(
//    val categoryIdx: Int,
//    val name: String
//)

data class RecordModifyData(
    val categoryInfo: List<RecordHomeCategoryInfo>,
    val itemInfo: List<RecordModifyItemInfo>
)

data class RecordModifyItemInfo(
    val categoryIdx: Int,
    val img: String,
    val itemIdx: Int,
    val name: String,
    var stocksCnt: Int
)