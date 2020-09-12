package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.R
import com.example.inventorybox.adapter.HomeOrderAdapter
import com.example.inventorybox.adapter.HomeOrderEditAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.network.PUT.RequestCheck
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_home_order_detail.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeOrderDetailActivity : AppCompatActivity() {

    lateinit var homeOrderAdapter : HomeOrderAdapter

    var datas_home = mutableListOf<HomeOrderData>()

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_order_detail)


        //발주 확인
        homeOrderAdapter = HomeOrderAdapter(this)
        //homeOrderAdapter.set_Listener(listener)
        rv_home_order_detail.adapter = homeOrderAdapter

        //발주 목록 통신
        homeOrderResponse()


        //메모 수정 클릭했을 때 새로운 액티비티
        tv_edit_memo.setOnClickListener {
            val intent = Intent(this, HomeOrderEditActivity::class.java)
            startActivity(intent)
        }

        btn_back_home_order_detail.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //홈 발주 확인 목록 통신
    private fun homeOrderResponse() {

        requestToServer.service.getHomeOrderResponse(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                Log.d("home main", "홈 디테일 발주 확인 목록 성공")

                for(data in it.data.result){
                    datas_home.add(data)
                }

                //발주할 재료 목록이 없으면
                if (datas_home.size == 0) {
                    rv_home_order_detail.visibility = View.GONE
                    iv_home_detail_none.visibility = View.VISIBLE
                }else{
                    rv_home_order_detail.visibility = View.VISIBLE
                    iv_home_detail_none.visibility = View.GONE
                }


                //발주 확인
                homeOrderAdapter.datas = datas_home
                homeOrderAdapter.notifyDataSetChanged()

            }
        )
    }


}