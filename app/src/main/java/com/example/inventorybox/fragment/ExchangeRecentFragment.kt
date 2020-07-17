package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ExchangeRVAdapter
import com.example.inventorybox.data.PostInfo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_exchange_recent.*

class ExchangeRecentFragment : Fragment() {

    var datas = mutableListOf<PostInfo>()
    lateinit var exchangeRVAdapter : ExchangeRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange_recent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeRVAdapter = ExchangeRVAdapter(view.context)
        exchangeRVAdapter.datas = datas
        rv_exchange_recent.adapter = exchangeRVAdapter
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }
    fun loadData(){

        datas = arrayListOf()
        RequestToServer.service.requestExchangeHomeData(
            getString(R.string.test_token),
           1
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.postInfo){
                    datas.add(data)
                }
                exchangeRVAdapter.datas=datas
                exchangeRVAdapter.notifyDataSetChanged()
//                rv_exchange_all.invalidate()
            }
        )
    }

}