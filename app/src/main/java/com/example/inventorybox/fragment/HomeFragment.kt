package com.example.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.R
import com.example.inventorybox.activity.HomeOrderDetailActivity
import com.example.inventorybox.activity.RecordRecordActivity
import com.example.inventorybox.activity.onHomeCheckListener
import com.example.inventorybox.adapter.CustomPagerAdapter
import com.example.inventorybox.adapter.HomeOrderAdapter
import com.example.inventorybox.adapter.HomeTodayOrderAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.etc.HomeTodayRecyclerViewDecoration
import com.example.inventorybox.network.PUT.HomeCheck
import com.example.inventorybox.network.PUT.RequestCheck
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment(private val drawerEvent : () -> Unit) : Fragment() {

    lateinit var homeOrderAdapter : HomeOrderAdapter

    var datas_home = mutableListOf<HomeOrderData>()
    var flag = mutableListOf<HomeOrderData>()

    lateinit var homeTodayOrderAdapter: HomeTodayOrderAdapter
    //lateinit var homeViewPagerAdapter: CustomPagerAdapter

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


        /*
        //체크 박스 리스너
        val listener = object : onHomeCheckListener {
            override fun onChange(position: Int, isChecked: Boolean, item_idx: Int, flag: Int) {
                val item_v = rv_home_today_order.layoutManager?.findViewByPosition(position)
                val image_v = item_v?.findViewById<ImageView>(R.id.iv_home_today_check)
                if(isChecked){
                    image_v?.setImageResource(R.drawable.home_ic_checked)
                }else{
                    image_v?.setImageResource(R.drawable.home_ic_notyet)
                }

                //체크 박스 통신
                requestHomeCheck(item_idx, isChecked)
            }
        }
         */



        //오늘 발주할 재료 확인
        homeTodayOrderAdapter = HomeTodayOrderAdapter(view.context)
        //rv_home_today_order.adapter = homeTodayOrderAdapter
        //rv_home_today_order.addItemDecoration(HomeTodayRecyclerViewDecoration())

        //발주 확인
        //homeOrderAdapter = HomeOrderAdapter(view.context)
        //homeOrderAdapter.set_Listener(listener)
        //rv_home_order.adapter = homeOrderAdapter

        //발주 목록 통신
        homeOrderResponse()

        //현재 날짜로 세팅
        currentDate()


        //버튼 눌렀을 때 drawer
        btn_toolbar_home.setOnClickListener {
            drawerEvent()
        }


        //발주 확인 상세보기 클릭했을 때 새로운 액티비티로
        btn_order_detail.setOnClickListener {
            activity?.let{
                val intent = Intent (it, HomeOrderDetailActivity::class.java)
                startActivityForResult(intent,0)
            }
        }
    }

    //현재 날짜로 세팅
    fun currentDate() {
        val current = LocalDateTime.now()
        val month = DateTimeFormatter.ofPattern("yyyy. MM.")
        val date = DateTimeFormatter.ofPattern("dd ")
        val day = DateTimeFormatter.ofPattern("E요일").withLocale(Locale.forLanguageTag("ko"))
        val formatted = current.format(month)
        val formatted2 = current.format(date)
        val formatted3 = current.format(day)

        tv_home_date.setText(formatted + formatted2 + formatted3)

    }

    //홈 발주 확인 목록 통신
    private fun homeOrderResponse() {

        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("home main", "홈 발주 확인 목록 성공")

                for(data in it.data.result){
                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
                }
                //발주 확인
                //homeOrderAdapter.datas = datas_home
                //homeOrderAdapter.notifyDataSetChanged()

                //home_viewpager.adapter = CustomPagerAdapter(childFragmentManager, datas_home)
                //homeViewPagerAdapter.notifyDataSetChanged()


                home_viewpager.adapter = CustomPagerAdapter(childFragmentManager, datas_home)
                tab.setupWithViewPager(home_viewpager)
                //오늘 발주할 재료 확인
//                homeTodayOrderAdapter.datas = datas_home
//                homeTodayOrderAdapter.notifyDataSetChanged()

            }
        )
    }


/*
    //체크 박스 통신
    private fun requestHomeCheck(item_idx : Int, isChecked: Boolean) {

        requestToServer.service.requestHomeCheck(
            getString(R.string.test_token), item_idx,
            RequestCheck(
                if(isChecked) 1 else 0
            )
        ).customEnqueue(
            onSuccess = {
                Log.d("checkbox", "체크 박스 성공")
            }
        )
    }
 */

    private fun homeCheckResponse() {
        requestToServer.service.getHomeOrderResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("home check flag", "홈 체크 성공")

                for(data in it.data.result){
                    flag.add(data)
                }
            }
        )
    }


}
