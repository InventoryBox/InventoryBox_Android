package com.example.inventorybox.data

data class RequestRecordItemModify(
    val date: String,
    val itemInfo: List<ResponseRecordCntItemInfo>
)

data class ResponseRecordCntItemInfo(
    val itemIdx: Int,
    val presentCnt: Int
)