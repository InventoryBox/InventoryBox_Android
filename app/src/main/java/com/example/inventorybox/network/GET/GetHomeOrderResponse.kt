package com.example.inventorybox.network.GET

import com.example.inventorybox.data.HomeOrderData

data class GetHomeOrderResponse(
    var status: Int,
    var success: Boolean,
    var message: String,
    var data: ArrayList<HomeOrderData>?
)