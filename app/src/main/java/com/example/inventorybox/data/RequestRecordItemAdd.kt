package com.example.inventorybox.data

data class RequestRecordItemAdd(
    val alarmCnt: Int,
    val categoryIdx: Int,
    val iconIdx: Int,
    val memoCnt: Int,
    val name: String,
    val unit: String
)