package com.inventorybox.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.activity.HomeOrderDetailActivity
import com.inventorybox.inventorybox.adapter.CustomPagerAdapter
import com.inventorybox.inventorybox.adapter.HomeOrderAdapter
import com.inventorybox.inventorybox.adapter.HomeTodayOrderAdapter
import com.inventorybox.inventorybox.data.HomeOrderData
import com.inventorybox.inventorybox.etc.showCustomToast
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class HomeFragment(private val drawerEvent : () -> Unit) : Fragment() {

    lateinit var homeOrderAdapter : HomeOrderAdapter

    var datas_home = mutableListOf<HomeOrderData>()
    var flag = -1

    lateinit var homeTodayOrderAdapter: HomeTodayOrderAdapter

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

        iv_home_none.visibility = View.VISIBLE
        //오늘 발주할 재료 확인
        homeTodayOrderAdapter = HomeTodayOrderAdapter(view.context)
        //rv_home_today_order.adapter = homeTodayOrderAdapter
        //rv_home_today_order.addItemDecoration(HomeTodayRecyclerViewDecoration())

        //발주 확인
        homeOrderAdapter = HomeOrderAdapter(view.context)
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
            SharedPreferenceController.getUserToken(context!!)
        ).customEnqueue(
            onSuccess = {
                Log.d("home main", "홈 발주 확인 목록 성공")

                //val check = view!!.findViewById<ImageView>(R.id.iv_home_today_check)

                for(data in it.data.result){
                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)
//                    datas_home.add(data)

                }

                //발주할 재료 목록이 없으면

                Log.d("home fragment", datas_home.toString())
                if (datas_home.size == 0) {
                    home_viewpager.visibility = View.GONE
                    iv_home_none.visibility = View.VISIBLE
                }else{
                    home_viewpager.visibility = View.VISIBLE
                    iv_home_none.visibility = View.GONE
                }

                home_viewpager?.adapter = CustomPagerAdapter(childFragmentManager, datas_home)
                tab.setupWithViewPager(home_viewpager)

            },
                onError = {
                    SharedPreferenceController.clearUserToken(context!!)
                    //MainActivity로 전달 - MainActivity 끝내기
                    context!!.showCustomToast("토큰이 만료되어 재로그인합니다.")
                    val intent = Intent("finish_activity")
                    context?.sendBroadcast(intent)
                }
        )
    }
}

