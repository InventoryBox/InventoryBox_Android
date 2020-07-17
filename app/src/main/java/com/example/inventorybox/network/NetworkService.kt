package com.example.inventorybox.network

import com.example.inventorybox.data.*
import com.example.inventorybox.network.POST.RequestEmail
import com.example.inventorybox.network.POST.ResponseLogin
import com.example.inventorybox.network.POST.RequestLogin
import com.example.inventorybox.network.POST.ResponseEmail
import com.example.inventorybox.network.PUT.RequestCheck
import com.example.inventorybox.network.PUT.RequestMemo
import com.example.inventorybox.network.PUT.ResponseHomeCheck
import com.example.inventorybox.network.PUT.ResponseMemo
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    @POST("/auth/email")
    fun requestEmail(
        @Body body: RequestEmail
    ): Call<ResponseEmail>

//    @Headers("Authorization: KakaoAK 13333b25e9a232d0fbf00fcc6cab2755")
    @GET("/v2/local/search/address.json")
    fun exchangeSearchLoca(
        @Header("Authorization")api : String,
        @Query("query") query: String
    ) : Call<ResponseSetLoca>


    /* 홈 */
    //홈 발주 목록
    @GET("/item/order")
    fun getHomeOrderResponse(
        @Header("token") token : String
    ): Call<ResponseHomeOrder>

    //홈 메모 수정
    @PUT("/item/order/memo")
    fun requestHomeMemo(
        @Header("token") token: String,
        @Body body: RequestMemo
    ): Call<ResponseMemo>

    //홈 체크박스 flag
    @PUT("/item/flag/{itemIdx}")
    fun requestHomeCheck(
        @Header("token") token: String,
        @Path("itemIdx") item_idx : Int,
        @Body body: RequestCheck
    ): Call<ResponseHomeCheck>


    /* 그래프 */
    @GET("/dashboard")
    fun requestGraphMainData(
        @Header("token") token : String
    ):Call<ResponseGraphHome>

    //재고기록 홈 뷰
    @GET("/record/home/{date}")
    fun getRecordHomeResponse(
        @Path("date") date : String,
        @Header("token") token: String
    ): Call<ResponseRecordHome>

    //재고기록 재료추가하기 뷰
    @GET("/record/item-add")
    fun getRecordAddResponse(
        @Header("token") token: String
    ):Call<ResponseRecordAdd>

    //재고기록 재료추가 데이터 보내기
    @POST("/record/item-add")
    fun postRecordAddResponse(
        @Header("token") token: String,
        @Body body: RequestRecordItemAdd
    ):Call<ResponseRecordItemAdd>


    //재고기록 기록수정 뷰
    @GET("/record/modifyView/{date}")
    fun getRecordModifyResponse(
        @Path("date") date : String,
        @Header("token") token: String
    ):Call<ResponseRecordModify>

    //재고기록 기록수정, 오늘 재고 기록 데이터 보내기
    @PUT("/record/modify")
    fun requestRecordModify(
        @Header("token") token: String,
        @Body body: RequestRecordItemModify
    )

    //재고기록 오늘재고기록하기 뷰
    @GET("/record/today")
    fun getRecordRecordRecord(
        @Header("token") token: String
    ):Call<ResponseRecordRecord>

    //재고기록 삭제
    @DELETE("/record/item-delete")
    fun deleteRecord(
        @Header("token") token: String,
        @Query("itemIdxList") item_idx: MutableList<Int>
    ):Call<ResponseSimple>

    //재고량 추이 제품별 디테일
    @GET("/dashboard/{item}/single")
    fun requestGraphDetailData(
        @Path("item") item_idx : Int,
        @Header("token") token: String,
        @Query("year") year : Int,
        @Query("month") month:Int
    ):Call<ResponseGraphDetail>

    // 재고량 추이 메모 수정
    @POST("/dashboard/{item}/modifyCnt")
    fun requestGraphDetailCountEdit(
        @Header("token") token : String,
        @Path("item") item_idx : Int,
        @Body body: RequestGraphDetailCountEdit
    ): Call<ResponseSimple>

    // 재고량 추이 비교 그래프
    @GET("/dashboard/{item}/double")
    fun requestGraphDetailComparativeData(
        @Path("item") item_idx : Int,
        @Header("token") token: String,
        @Query("week[0]") week1: String,
        @Query("week[1]") week2: String
    ): Call<ResponseGraphDetailComparativeData>


    /* 재고교환 */
        // 재고교환 홈
    @GET("/exchange/{filter}")
    fun requestExchangeHomeData(
        @Header("token") token: String,
        @Path("filter") filter : Int
    ):Call<ResponseExchangeHomeData>

    // 재고교환 재료 detail 가져오기
    @GET("/exchange/post/{postIdx}")
    fun requestExchangeItemDetail(
        @Header("token") token: String,
        @Path("postIdx") post_idx : Int
    ):Call<ResponseExchangeItemDetail>

    // 재고교환 게시물 등록
    @Multipart
    @POST("/exchange/post")
    fun postExchangeItem(
        @Header("token") token : String,
        @Part file : MultipartBody.Part,
        @PartMap info : HashMap<String,@JvmSuppressWildcards RequestBody>
    ): Call<ResponsePostExchangeItem>

    // 재고교환 주소 업데이트
    @POST("/exchange/modifyLoc")
    fun requestExchangeLocationEdit(
        @Header("token") token : String,
        @Body body :RequestExchangeLocationEditData
    ): Call<ResponseSimple>

    // 재고교환 게시글 등록 기본 정보 불러오기
    @GET("/exchange/user/info")
    fun requestExchangeUserInfo(
        @Header("token") token : String
    ): Call<ResponseExchangeUserInfo>
}