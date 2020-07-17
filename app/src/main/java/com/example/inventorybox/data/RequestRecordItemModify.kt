package com.example.inventorybox.data

data class RequestRecordItemModify(
    val date: String,
    val itemInfo: ArrayList<ResponseRecordCntItemInfo>
)

data class ResponseRecordCntItemInfo(
    val itemIdx: Int,
    val presentCnt: Int
)