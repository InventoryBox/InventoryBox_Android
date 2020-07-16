package com.example.inventorybox.data

data class ResponseGraphDetail(
    val data: GraphDetailData
)

data class GraphDetailData(
    val alarmCnt: Int,
    val memoCnt: Int,
    val graphInfo: ArrayList<GraphInfo>,
    val weeksCnt: Int
)