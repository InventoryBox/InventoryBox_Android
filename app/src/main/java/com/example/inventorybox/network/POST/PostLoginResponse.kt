package com.example.inventorybox.network.POST

import android.service.autofill.UserData

data class PostLoginResponse (
    val status : Int,
    val success : Boolean,
    val message : String,
    val data : UserData?    //사용자 인증 정보를 저장할 토큰값 (null값을 가질 수 있음)
)