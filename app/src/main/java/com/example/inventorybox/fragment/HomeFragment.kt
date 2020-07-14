package com.example.inventorybox.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.R
import com.example.inventorybox.adapter.HomeOrderAdapter
import com.example.inventorybox.adapter.HomeTodayOrderAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.etc.HomeTodayRecyclerViewDecoration
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.GET.ResponseHomeOrder
import com.example.inventorybox.network.NetworkService
import com.example.inventorybox.network.POST.RequestLogin
import com.example.inventorybox.network.POST.ResponseLogin
import com.example.inventorybox.network.RequestToServer
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment(private val drawerEvent : () -> Unit) : Fragment() {

    lateinit var homeOrderAdapter : HomeOrderAdapter
    //var datas = mutableListOf<HomeOrderData>()

    lateinit var homeTodayOrderAdapter: HomeTodayOrderAdapter
    //var datas2 = mutableListOf<HomeTodayOrderData>()

    val requestToServer = RequestToServer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listener = object : onHomeCheckListener{
            override fun onChange(position: Int, isChecked: Boolean) {
                val item_v = rv_home_today_order.layoutManager?.findViewByPosition(position)
                val image_v = item_v?.findViewById<ImageView>(R.id.iv_home_today_check)
                image_v?.setImageResource(R.drawable.home_ic_checked)
            }

        }
        //오늘 발주할 재고 확인
        homeTodayOrderAdapter = HomeTodayOrderAdapter(view.context)
        rv_home_today_order.adapter = homeTodayOrderAdapter
        rv_home_today_order.addItemDecoration(HomeTodayRecyclerViewDecoration())
        //homeTodayOrderAdapter.datas = datas
        //homeTodayOrderAdapter.notifyDataSetChanged()


        //발주 확인
       homeOrderAdapter = HomeOrderAdapter(view.context)
        homeOrderAdapter.set_Listener(listener)
        rv_home_order.adapter = homeOrderAdapter
        //rv_home_order.addItemDecoration(HomeOrderRecyclerViewDecoration())


        //통신
        homeOrderResponse()


        //현재 날짜로 세팅
        currentDate()

        //리사이클러뷰 스크롤 중복 막기
        rv_home_order.setOverScrollMode(View.OVER_SCROLL_NEVER)

        //플로팅 버튼 눌렀을 때 최상단으로 이동
        iv_floating_btn.setOnClickListener {
            scrollview_home.smoothScrollTo(0, 0)
        }

        //버튼 눌렀을 때 drawer
        btn_toolbar_home.setOnClickListener {
            drawerEvent()
        }


        //메모 수정 클릭했을 때 새로운 프래그먼트로
        tv_edit_memo.setOnClickListener {
            val fragment = HomeOrderEditFragment()
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.add(R.id.frame_layout, fragment, "homeOrdercheck")
            transaction.addToBackStack(null) //해당 transaction을 백스택에 저장
            transaction.commit() //transaction 실행
        }



    }

    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val month = DateTimeFormatter.ofPattern("yyyy년 MM월")
        val date = DateTimeFormatter.ofPattern("dd")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val formatted = current.format(month)
        val formatted2 = current.format(date)
        val formatted3 = current.format(day)

        //Log.d("date", "#############$formatted")

        tv_home_month.setText(formatted)
        tv_home_date.setText(formatted2)
        tv_home_day.setText(formatted3)

    }

    //홈 발주 확인 통신
    private fun homeOrderResponse() {

        requestToServer.service.getHomeOrderResponse(
        ).enqueue(object : Callback<ResponseHomeOrder>{
            override fun onFailure(call: Call<ResponseHomeOrder>, t: Throwable) {
                Log.e("#############Fail", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseHomeOrder>,
                response: Response<ResponseHomeOrder>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 200){
                        val tmp: ArrayList<HomeOrderData> = response.body()!!.data!!
                        homeOrderAdapter.datas = tmp
                        homeOrderAdapter.notifyDataSetChanged()
                    }
                }
            }
        })


    }

    /*
    //홈 그래프 통신
    private fun homeGraphResponse() {
        val getHomeGraphResponse = networkService.getHomeGraphResponse("application/json")

        getHomeGraphResponse.enqueue(object : Callback<GetHomeGraphResponse>{
            override fun onFailure(call: Call<GetHomeGraphResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<GetHomeGraphResponse>,
                response: Response<GetHomeGraphResponse>
            ) {

            }
        })
    }
    */

    //발주 확인
    /*private fun loadHomeOrderDatas(){
        datas.apply {
            add(
                HomeOrderData(
                    index = 0,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 1,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "녹차 파우더",
                    count = 5,
                    unit = "봉지"
                )
            )
            add(
                HomeOrderData(
                    index = 2,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 8,
                    unit = "덩어리"
                )
            )
            add(
                HomeOrderData(
                    index = 3,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "모카 파우더",
                    count = 10,
                    unit = "봉지"
                )
            )
            add(
                HomeOrderData(
                    index = 4,
                    img = "https://cdn.pixabay.com/photo/2020/05/03/13/09/puppy-5124947_1280.jpg",
                    name = "원두",
                    count = 4,
                    unit = "팩"
                )
            )
            add(
                HomeOrderData(
                    index = 5,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "헤이즐넛 시럽",
                    count = 2,
                    unit = "덩어리"
                )
            )
            add(
                HomeOrderData(
                    index = 6,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "우유",
                    count = 10,
                    unit = "덩어리"
                )
            )
            add(
                HomeOrderData(
                    index = 7,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "딸기",
                    count = 10,
                    unit = "덩어리"
                )
            )
            add(
                HomeOrderData(
                    index = 8,
                    img = "https://cdn.pixabay.com/photo/2016/01/05/17/51/dog-1123016__340.jpg",
                    name = "원두",
                    count = 10,
                    unit = "덩어리"
                )
            )

        }


        homeOrderAdapter.datas = datas
        homeOrderAdapter.notifyDataSetChanged()

    }*/
}

interface onHomeCheckListener{
    fun onChange(position : Int, isChecked : Boolean)
}