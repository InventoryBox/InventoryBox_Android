package com.example.inventorybox.network.POST

import android.service.autofill.UserData

data class PostLoginResponse (
    val status : Int,   //status상태 코드
    val success : Boolean,  //성공여부
    val message : String,   //메시지
    val data : UserData?    //사용자 인증 정보를 저장할 토큰값 (null값을 가질 수 있음)
)