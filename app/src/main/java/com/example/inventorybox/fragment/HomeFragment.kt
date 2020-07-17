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
import com.example.inventorybox.network.PUT.HomeCheck
import com.example.inventorybox.network.PUT.RequestCheck
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment(private val drawerEvent : () -> Unit) : Fragment() {

    lateinit var homeOrderAdapter : HomeOrderAdapter

    var datas_home = mutableListOf<HomeOrderData>()

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
            override fun onChange(position: Int, isChecked: Boolean, item_idx: Int, flag: Int) {
                val item_v = rv_home_today_order.layoutManager?.findViewByPosition(position)
                val image_v = item_v?.findViewById<ImageView>(R.id.iv_home_today_check)
                if(isChecked){
                    image_v?.setImageResource(R.drawable.home_ic_checked)
                }else{
                    image_v?.setImageResource(R.drawable.home_ic_notyet)
                }

                requestHomeCheck(item_idx, flag)
            }
        }
        //오늘 발주할 재료 확인
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


        //목록 통신
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

    //홈 발주 확인 목록 통신
    private fun homeOrderResponse() {

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("##############", "홈 발주 확인 목록 성공")
                for(data in it.data.result){
                    datas_home.add(data)
                }
                //발주 확인
                homeOrderAdapter.datas = datas_home
                homeOrderAdapter.notifyDataSetChanged()

                //오늘 발주할 재료 확인
                homeTodayOrderAdapter.datas = datas_home
                homeTodayOrderAdapter.notifyDataSetChanged()

            }
        )
    }


    //체크 박스 통신
    private fun requestHomeCheck(item_idx : Int, flag: Int) {

        requestToServer.service.requestHomeCheck(
            getString(R.string.test_token), item_idx,
            RequestCheck(flag = flag)
        ).customEnqueue(
            onSuccess = {
                Log.d("##############", "체크 박스 성공")
            }
        )
    }

}

interface onHomeCheckListener{
    fun onChange(position : Int, isChecked : Boolean, item_idx: Int, flag: Int)
}