package com.example.inventorybox.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.inventorybox.R
import com.example.inventorybox.data.Address
import com.example.inventorybox.data.RequestExchangeLocationEditData
import com.example.inventorybox.etc.ExchangeEnqueue
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.NetworkService
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_set_location.*
import kotlinx.android.synthetic.main.layout_custom_toast.view.*

class ExchangeSetLocation : AppCompatActivity() {

    lateinit var adapter : ExchangeSearchLocationAdapter

    var datas = ArrayList<Address>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_set_location)

        // 아이템 클릭하면 et_location_search 에 뜨도록
        val adapter_listener = object: MyItemClickListener{

            override fun onClick(address: Address) {
                et_location_search.setText(address.address_name)
                postChangedLoca(address.address_name, address.x, address.y)
            }
        }
        // 검색 결과 나오는 창
        adapter = ExchangeSearchLocationAdapter(this)
        adapter.datas = datas
        adapter.setListener(adapter_listener)
        exchange_rv_search_loca.adapter = adapter

        btn_set_loca_search.setOnClickListener {
            searchFromNetwork(et_location_search.text.toString())
        }

//        et_location_search.addTextChangedListener(object : TextWatcher{
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, p2: Int, end: Int) {
//                searchFromNetwork(s.toString())
//            }
//        })

        //나가기 버튼
        btn_finish.setOnClickListener {
            finish()
        }

    }

    private fun postChangedLoca(address: String, x: Double, y: Double) {
        RequestToServer.service.requestExchangeLocationEdit(
            getString(R.string.test_token),
            RequestExchangeLocationEditData(
                address,
                y,
                x
            )
        ).customEnqueue(
            onSuccess = {
                finish()
//                Log.d("testtest", address + "set success")
            }
        )
    }

    fun searchFromNetwork(query : String){
        datas = ArrayList()
        RequestToServer.k_service.exchangeSearchLoca(
                getString(R.string.kakao_rest_api_key),
            query
        )
            .ExchangeEnqueue(
                onSuccess = {
                    if(it.documents!=null){
                        for(doc in it.documents){
                            datas.add(doc.address)
//                            if(doc.road_address!=null)
//                                datas.add(doc.road_address!!.address_name)
                        }
                    }
                    Log.d("exchangeSetLocation", datas.toString())
                    if(datas.isEmpty()){
                        showToast(this, "검색 결과가 없습니다")
                    }
                    adapter.datas = datas
                    adapter.notifyDataSetChanged()
                }
                ,onFail = {
                }
            )
    }


    interface MyItemClickListener{
        fun onClick(address : Address)
    }

    fun showToast(context: Context, message : String){
        val inflater: LayoutInflater = LayoutInflater.from(context)


        val toast_view : View = inflater.inflate(R.layout.layout_custom_toast, null)

        toast_view.toast_message.text=message
        val toast= Toast(context)
        toast.view=toast_view
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
        toast.setGravity(Gravity.BOTTOM,0,300)

    }

}