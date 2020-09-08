package com.example.inventorybox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.adapter.ExchangeRVAdapter
import com.example.inventorybox.data.PostInfo
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_exchange_all.*
import kotlinx.android.synthetic.main.fragment_exchange_my_like.*


class ExchangeMyLike : Fragment() {

    lateinit var exchangeRVAdapter: ExchangeRVAdapter
    var datas = mutableListOf<PostInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange_my_like, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeRVAdapter = ExchangeRVAdapter(view.context)
        rv_exchange_like.adapter = exchangeRVAdapter
        rv_exchange_like.addItemDecoration(HomeOrderRecyclerViewDecoration())

        // 뒤로가기
        btn_back.setOnClickListener {
            val transaction: FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.remove(this).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        getDatas()
    }

    fun getDatas(){
        RequestToServer.service.getExchangeLike(
            SharedPreferenceController.getUserToken(context!!)
        ).customEnqueue(
            onSuccess = {
                datas = mutableListOf()
                for(data in it.data.postInfo){
                    datas.add(data)
                }
                exchangeRVAdapter.datas=datas
                exchangeRVAdapter.notifyDataSetChanged()
            }
        )
    }

}