package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ExchangeRVAdapter
import com.example.inventorybox.data.ExchangeData
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.etc.HomeTodayRecyclerViewDecoration
import kotlinx.android.synthetic.main.fragment_exchange_all.*

class ExchangeAllFragment : Fragment() {

    lateinit var exchangeRVAdapter: ExchangeRVAdapter
    var datas = mutableListOf<ExchangeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exchangeRVAdapter = ExchangeRVAdapter(view.context)
        rv_exchange_all.adapter = exchangeRVAdapter
        rv_exchange_all.addItemDecoration(HomeOrderRecyclerViewDecoration())
        loadDatas()
    }

    private fun loadDatas(){
        datas.apply {
            add(ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전"))
            add(ExchangeData(1, "https://cdn.pixabay.com/photo/2018/10/01/09/21/pets-3715733_1280.jpg", "8,000원", "700m", "일회용 샐러드 팩", "유통기한 없음", "3분 전"))
            add(ExchangeData(2, "https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초전"))
            add(ExchangeData(3, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초전"))
            add(ExchangeData(4, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초전"))
        }
        exchangeRVAdapter.datas = datas
        exchangeRVAdapter.notifyDataSetChanged()

    }


}