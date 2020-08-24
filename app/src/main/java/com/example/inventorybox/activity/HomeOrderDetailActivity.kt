package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.HomeOrderAdapter
import com.example.inventorybox.adapter.HomeOrderEditAdapter
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.network.PUT.RequestCheck
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_home_order_detail.*

class HomeOrderDetailActivity : AppCompatActivity() {

    lateinit var homeOrderAdapter : HomeOrderAdapter

    var datas_home = mutableListOf<HomeOrderData>()

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_order_detail)


        //체크 박스 리스너
        val listener = object : onHomeCheckListener {
            override fun onChange(position: Int, isChecked: Boolean, item_idx: Int, flag: Int) {


                //체크 박스 통신
                requestHomeCheck(item_idx, isChecked)
            }
        }

        //발주 확인
        homeOrderAdapter = HomeOrderAdapter(this)
        homeOrderAdapter.set_Listener(listener)
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
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("home main", "홈 디테일 발주 확인 목록 성공")
                var tmp = datas_home
                if (tmp.isEmpty()){
//                    empty_img.visibility = View.VISIBLE
                }
                for(data in it.data.result){
                    datas_home.add(data)
                }
                //발주 확인
                homeOrderAdapter.datas = datas_home
                homeOrderAdapter.notifyDataSetChanged()

            }
        )
    }


    //체크 박스 통신
    private fun requestHomeCheck(item_idx : Int, isChecked: Boolean) {

        requestToServer.service.requestHomeCheck(
            getString(R.string.test_token), item_idx,
            RequestCheck(
                if(isChecked) 1 else 0
            )
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