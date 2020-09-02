package com.example.inventorybox.network

import com.example.inventorybox.ExchangeMyPost
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

    //이메일 찾기
    @POST("/auth/find-email")
    fun requestFindEamil(
        @Header("Authorization") token : String,
        @Body body: RequestFindEmail
    ): Call<ResponseFindEmail>

    //회원가입 api
    @POST("/auth/email")
    fun requestEmail(
        @Body body: RequestEmail
    ): Call<ResponseEmail>

    @POST("/auth/nickname")
    fun requestNicknameCheck(
        @Body body :RequestNicknameCheck
    ): Call<ResponseNicknameCheck>

//    @Headers("Authorization: KakaoAK 13333b25e9a232d0fbf00fcc6cab2755")
    @GET("/v2/local/search/address.json")
    fun exchangeSearchLoca(
        @Header("Authorization") token : String,
        @Query("query") query: String
    ) : Call<ResponseSetLoca>


    /* 햄버거 바 */
    //drawer에 유저 정보 가져오기
    @GET("/auth/user/profile")
    fun getHomePersonal(
        @Header("token") token: String
    ): Call<ResponseProfile>

    //닉네임, 사진 가져오기
    @GET("/auth/user/profile")
    fun getProfile(
        @Header("token") token: String
    ): Call<ResponseProfile>

    //프로필 변경
    @Multipart
    @PUT("/auth/signup")
    fun requestSignUp(
        @Part file : MultipartBody.Part,
        @PartMap info : HashMap<String,@JvmSuppressWildcards RequestBody>
    ) : Call<ResponseModProfile>

    //프로필 변경(햄버거바)
    @Multipart
    @PUT("/auth/user/profile")
    fun requestProfile2(
        @Part file : MultipartBody.Part,
        @Body body: RequestProfile
    ) : Call<ResponseModProfile>

    //개인 정보 가져오기
    @GET("/auth/user/personal")
    fun getPersonal(
        @Header("token") token: String
    ): Call<ResponsePersonalData>

    //개인 정보 변경
    @PUT("/auth/user/personal")
    fun requestPersonal(
        @Header("token") token : String,
        @Body body: RequestPersonal
    ): Call<ResponsePersonal>


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
    @PUT("/item/flag/{itemIdx}/{flag}")
    fun HomeCheck(
        @Path("itemIdx") item_idx : Int,
        @Path("flag") flag : Int
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
    ): Call<ResponseSimple>

    //재고기록 오늘재고기록하기 뷰
    @GET("/record/today")
    fun getRecordRecordRecord(
        @Header("token") token: String
    ):Call<ResponseRecordRecord>

    //재고기록 삭제
//    @DELETE("/record/item-delete")
    @DELETE("/record/item-delete/{itemIdxList}")
    fun deleteRecord(
        @Header("token") token: String,
        @Path("itemIdxList") item_idx : String
    ):Call<ResponseSimple>

    //재고기록 카테고리 삭제
    @DELETE("/record/category-delete/{category_idx}")
    fun deleteCategory(
        @Header("token") token: String,
        @Path("category_idx") category_idx : Int
    ):Call<ResponseSimple>

    // 재고기록 카테고리 이동
    @PUT("/record/category-move")
    fun moveCategory(
        @Header("token") token: String,
        @Body body : RequestRecordDelete
    ): Call<ResponseSimple>

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

    // 재고교환 게시글 수정
    @Multipart
    @PUT("/exchange/post/modify")
    fun postExchangeModify(
        @Header("token") token : String,
        @Part file : MultipartBody.Part?,
        @PartMap info : HashMap<String,@JvmSuppressWildcards RequestBody>
    ): Call<ResponseSimple>

    // 재고교환 주소 업데이트
    @POST("/exchange/modifyLoc")
    fun requestExchangeLocationEdit(
        @Header("token") token : String,
        @Body body :RequestExchangeLocationEditData
    ): Call<ResponseSimple>

    // 재고교환 게시글 삭제
    @DELETE("/exchange/post/{postIdx}")
    fun requestExchangePostDelete(
        @Header("token")token : String,
        @Path("postIdx") postIdx : Int
    ): Call<ResponseSimple>

    // 재고교환 좋아요 상태 업데이트
    @PUT("/exchange/post/like-status")
    fun requestExchangeLikeStatus(
        @Header("token") token: String,
        @Body body :RequestExchangeLikeStatus
    ): Call<ResponseLikeStatus>


    @PUT("/exchange/post/modifyStatus")
    fun requestExchangeSoldStatus(
        @Header("token") token: String,
        @Body body :RequestExchangeLikeStatus
    ): Call<ResponseSimple>

    // 재고교환 게시글 등록 기본 정보 불러오기
    @GET("/exchange/user/info")
    fun requestExchangeUserInfo(
        @Header("token") token : String
    ): Call<ResponseExchangeUserInfo>

    // 재고교환 게시글 수정 화면 조회
    @GET("/exchange/post/modify/{post_idx}")
    fun requestExchangeModifyDetail(
    @Header("token") token: String

    ):Call<ResponseExchangeItemDetail>

    // 재고교환 내가 쓴 게시물
    @GET("/exchange/user/post")
    fun getExchangeMyPost(
        @Header("token") token : String
    ):Call<ResponseExchangeMyPost>

    // 재고교환 찜 목록
    @GET("/exchange/favorite/list")
    fun getExchangeLike(
        @Header("token") token : String
    ):Call<ResponseExchangeLike>
//    // 재고교환 좋아요 상태 수정
//    @PUT("/exchange/post/like-status")
//    fun requestExchangeLikeStatus(
//        @Header("token") token : String,
//        @Body body : RequestExchangeLikeStatus
//    )

    // 재고교환 검색
    @GET("/exchange/search/{keyword}/0")
    fun requestExchangeSearch(
        @Header("token") token : String,
        @Path("keyword") keyword : String
    ): Call<ResponseExchangeSearch>



    // 재고기록 카테고리 정보 조회
    @GET("/record/folder/category-info")
    fun requestCategorySetInfo(
        @Header("token") token : String
    ):Call<ResponseCategorySet>

    //재고기록 카테고리 추가
    @POST("/record/category-add")
    fun requestCategoryAdd(
        @Header("token") token : String,
        @Body body: RequestCategoryAdd
    ): Call<ResponseCategoryAdd>


}