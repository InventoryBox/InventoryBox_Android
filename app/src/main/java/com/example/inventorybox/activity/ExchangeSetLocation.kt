package com.example.inventorybox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.inventorybox.R
import com.example.inventorybox.etc.ExchangeEnqueue
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.NetworkService
import com.example.inventorybox.network.RequestToServer
import kotlinx.android.synthetic.main.activity_exchange_set_location.*

class ExchangeSetLocation : AppCompatActivity() {

    lateinit var adapter : ExchangeSearchLocationAdapter
    var datas = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_set_location)

        // 아이템 클릭하면 et_location_search 에 뜨도록
        val adapter_listener = object: MyItemClickListener{
            lateinit var s: String
            override fun onClick(s: String) {
                et_location_search.setText(s)
                this.s = s
            }
        }
        // 검색 결과 나오는 창
        adapter = ExchangeSearchLocationAdapter(this)
        adapter.datas = datas
        adapter.setListener(adapter_listener)
        exchange_rv_search_loca.adapter = adapter

        et_location_search.addTextChangedListener(object : TextWatcher{

            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, p2: Int, end: Int) {
                searchFromNetwork(s.toString())
            }
        })


        /*btn_set_loca_search.setOnClickListener {
            datas = ArrayList()
            val quest = et_location_search.text.toString()
            RequestToServer.k_service.exchangeSearchLoca(
//                R.string.kakao_rest_api_key.toString(),
                quest
//                "신림"
            )
                .ExchangeEnqueue(
                    onSuccess = {
                        if(it.documents!=null){
                            for(doc in it.documents){
                                datas.add(doc.address_name)
                                if(doc.road_address!=null)
                                    datas.add(doc.road_address!!.address_name)
                            }
                        }
                        adapter.datas = datas
                        adapter.notifyDataSetChanged()
                    }
                    ,onFail = {
                        Log.d("testtest","fail")
                    }
                )
        }*/
        //나가기 버튼
        btn_finish.setOnClickListener {
            finish()
        }

    }
    fun searchFromNetwork(query : String){
        datas = ArrayList()
        RequestToServer.k_service.exchangeSearchLoca(
//                R.string.kakao_rest_api_key.toString(),
            query
//                "신림"
        )
            .ExchangeEnqueue(
                onSuccess = {
                    if(it.documents!=null){
                        for(doc in it.documents){
                            datas.add(doc.address_name)
                            if(doc.road_address!=null)
                                datas.add(doc.road_address!!.address_name)
                        }
                    }
                    adapter.datas = datas
                    adapter.notifyDataSetChanged()
                }
                ,onFail = {
                    Log.d("testtest","fail")
                }
            )
    }

    interface MyItemClickListener{
        fun onClick(s : String)
    }
}