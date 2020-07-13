package com.example.inventorybox.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RequestToServer {

    val retrofit_kakao  = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val k_service = retrofit_kakao.create(NetworkService::class.java)
}