package com.inventorybox.inventorybox.data

data class ResponseExchangeUserInfo(
    val data: SomeData2,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class SomeData2(
    val userInfo: UserInfo
)

data class UserInfo(
    val coName: String,
    val location: String,
    val phoneNumber: String,
    val repName: String
)