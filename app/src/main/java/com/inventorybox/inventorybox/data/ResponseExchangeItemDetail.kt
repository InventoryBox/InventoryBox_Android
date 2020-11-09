package com.inventorybox.inventorybox.data

data class ResponseExchangeItemDetail(
    val data: DataLoca,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class DataLoca(
    val itemInfo: PostDetailInfo,
    val userInfo: PostDetailUser
)


data class PostDetailInfo(
    val coverPrice: Int,
    val description: String,
    val expDate: String,
    val isFood: Int,
    val isSold: Int,
    val distDiff:Int,
    val postIdx: Int,
    val price: Int,
    val productImg: String,
    val productName: String,
    val quantity: Int,
    val unit: String,
    val uploadDate: String,
    val userIdx: Int,
    val userCheck : Int
)


data class PostDetailUser(
    val coName: String,
    val location: String,
    val phoneNumber: String,
    val repName: String
)