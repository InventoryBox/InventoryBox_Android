package com.example.inventorybox.network

import android.app.Application
import com.example.inventorybox.R
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApplicationController : Application() {

    //manifest에 추가해야 함
    //통신하고자 하는 API 서버의 기본 주소
    private val baseURL = ""

    lateinit var networkService: NetworkService
//    lateinit var k_service : NetworkService

    companion object{
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        buildNetwork()

        KakaoSdk.init(this, "3870475473bd9fb4ab292c7b2c0276bd", true)
    }

    //Retrofit 객체 생성
    private fun buildNetwork() {
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(baseURL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        val retrofit_kakao  = Retrofit.Builder()
//            .baseUrl("https://dapi.kakao.com")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
        //Retrofit 객체 활성화
//        networkService = retrofit.create(NetworkService::class.java)

//        k_service = retrofit_kakao.create(NetworkService::class.java)

    }
}