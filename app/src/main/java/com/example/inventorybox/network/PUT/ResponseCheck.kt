package com.example.inventorybox.network.PUT


data class ResponseHomeCheck(
    var data: HomeCheck
)

data class HomeCheck(
    var result: ArrayList<Item>
)

data class Item(
    var itemIdx: Int,
    var flag: Int
)