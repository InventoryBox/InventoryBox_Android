package com.example.inventorybox.network

import com.example.inventorybox.network.GET.ResponseHomeOrder
import com.example.inventorybox.data.ResponseGraphHome
import com.example.inventorybox.data.ResponseSetLoca
import com.example.inventorybox.network.POST.ResponseLogin
import com.example.inventorybox.network.POST.RequestLogin
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {
    //post방식은 HTTP Request의 Body에 Json 포맷으로 데이터를 담아서 전달
    //로그인 api
    @POST("/auth/signin")
    fun requestLogin(@Body body: RequestLogin): Call<ResponseLogin>

    //회원가입 api
    @POST("/auth/signup")
    fun postSignupResponse(
        @Header("Content-Type") content_type: String,
        @Body body: JsonObject
    ): Call<ResponseLogin>

//    @Headers("Authorization: KakaoAK 13333b25e9a232d0fbf00fcc6cab2755")
    @GET("/v2/local/search/address.json")
    fun exchangeSearchLoca(
        @Header("Authorization")api : String,
        @Query("query") query: String
    ) : Call<ResponseSetLoca>

    //홈 발주 목록
    @GET("/item/order")
    fun getHomeOrderResponse(): Call<ResponseHomeOrder>

    @GET("/dashboard")
    fun requestGraphMainData(
        @Header("token") token : String
    ):Call<ResponseGraphHome>

}