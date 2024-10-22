package com.inventorybox.inventorybox

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.activity.ExchangePostActivity
import com.inventorybox.inventorybox.adapter.ExchangeRVAdapter
import com.inventorybox.inventorybox.data.PostInfo
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_exchange_search.*

class ExchangeSearch : Fragment() {

    lateinit var exchangeRVAdapter: ExchangeRVAdapter
    var datas = mutableListOf<PostInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_exchange_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exchangeRVAdapter = ExchangeRVAdapter(view.context)
        rv_exchange_search.adapter = exchangeRVAdapter

        // 검색어 지우기
        tv_exchange_search_delete.setOnClickListener {
            exchange_search_et.setText("")
        }

        //뒤로가기
        btn_finish_exchange_search.setOnClickListener {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }

        // 게시글 작성
        exchange_search_floating_btn.setOnClickListener {
            val intent = Intent(it.context, ExchangePostActivity::class.java)
            startActivity(intent)
//            it.context.startActivity(intent)
        }
        exchange_search_et.addTextChangedListener(
            object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {
                    searchFromServer(s.toString())
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            }
        )
    }

    private fun searchFromServer(s: String) {
        RequestToServer.service.requestExchangeSearch(
            SharedPreferenceController.getUserToken(context!!),
            s
        ).customEnqueue(
            onSuccess = {
                datas = mutableListOf()
                    Log.d("exchangeSearch", it.data.toString())
                    Log.d("exchangeSearch", s)

                if(it.data.postInfo.isNotEmpty()){
                    for(data in it.data.postInfo){
                        datas.add(data)
                    }
                    exchangeRVAdapter.datas = datas
                    exchangeRVAdapter.notifyDataSetChanged()
                    tv_exchange_search_cnt.setText("(${datas.count()})")
                }
            }
        )
    }
}