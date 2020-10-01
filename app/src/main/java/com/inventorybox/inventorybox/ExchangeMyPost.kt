package com.inventorybox.inventorybox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.adapter.ExchangeMyPostAdapter
import com.inventorybox.inventorybox.data.PostInfo
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_my_post.*

class ExchangeMyPost : AppCompatActivity() {

    var datas_sold = mutableListOf<PostInfo>()
    var datas_not_sold = mutableListOf<PostInfo>()

    val adapter_sold = ExchangeMyPostAdapter(this, true)
    val adapter_not_sold = ExchangeMyPostAdapter(this,false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_my_post)
        rv_exchange_mypost_main.adapter = adapter_not_sold
        rv_exchange_mypost_sold.adapter = adapter_sold
//        loadData()

        // 뒤로가기 버튼
        exchange_mypost_finish.setOnClickListener {
            finish()
        }
    }

    private fun loadData() {

        datas_not_sold = mutableListOf()
        datas_sold= mutableListOf()
        RequestToServer.service.getExchangeMyPost(
            SharedPreferenceController.getUserToken(this)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.itemInfo){
                    if(data.isSold==1){
                        datas_sold.add(data)
                    }else{
                        datas_not_sold.add(data)
                    }
                }
                adapter_not_sold.datas = datas_not_sold
                adapter_sold.datas = datas_sold

                adapter_not_sold.notifyDataSetChanged()
                adapter_sold.notifyDataSetChanged()

            }
        )
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}