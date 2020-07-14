package com.example.inventorybox.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer {
    private val baseURL = "http//ec2-13-209-128-238.ap-northeast-2.compute.amazonaws.com:3000"
//    private val baseURL = "ec2-13-209-128-238.ap-northeast-2.compute.amazonaws.com:3000"
    val retrofit_kakao  = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://ec2-13-209-128-238.ap-northeast-2.compute.amazonaws.com:3000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val k_service = retrofit_kakao.create(NetworkService::class.java)
    val service = retrofit.create(NetworkService::class.java)
}