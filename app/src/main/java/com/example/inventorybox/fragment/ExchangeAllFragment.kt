package com.example.inventorybox.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ExchangeRVAdapter
import com.example.inventorybox.data.ExchangeData
import com.example.inventorybox.etc.HomeOrderRecyclerViewDecoration
import com.example.inventorybox.etc.HomeTodayRecyclerViewDecoration
import kotlinx.android.synthetic.main.fragment_exchange_all.*
import kotlinx.android.synthetic.main.fragment_home_order_edit.*

class ExchangeAllFragment : Fragment() {

    lateinit var exchangeRVAdapter: ExchangeRVAdapter

    // -1, 0, 1 순으로 거리순, 최신순, 가격순
    var sort_idx = 1


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

        // 거리순, 최신순, 가격순 설정
        val listener_sort = View.OnClickListener{
            if(it==btn_sort_price){
                btn_sort_distance.categorySetUnClicked(view.context)
                btn_sort_recent.categorySetUnClicked(view.context)
                btn_sort_price.categorySetClicked(view.context)
            }else if(it==btn_sort_distance){
                btn_sort_distance.categorySetClicked(view.context)
                btn_sort_recent.categorySetUnClicked(view.context)
                btn_sort_price.categorySetUnClicked(view.context)
            }else{
                btn_sort_distance.categorySetUnClicked(view.context)
                btn_sort_recent.categorySetClicked(view.context)
                btn_sort_price.categorySetUnClicked(view.context)
            }
        }
        btn_sort_distance.setOnClickListener(listener_sort)
        btn_sort_recent.setOnClickListener(listener_sort)
        btn_sort_price.setOnClickListener(listener_sort)



        loadDatas()

        // we are inventory box - by yeonho choi

        rv_exchange_all.setOverScrollMode(View.OVER_SCROLL_NEVER)
    }

    private fun loadDatas(){
        val datas = mutableListOf(
            ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전"),
            ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전"),
            ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전"),
            ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전"),
            ExchangeData(0, "https://cdn.pixabay.com/photo/2019/08/19/07/45/pets-4415649_1280.jpg", "7,000원", "100m", "녹차 라떼 파우더", "유통기한 2020. 12. 23", "10초 전")
        )
        exchangeRVAdapter.datas = datas
        exchangeRVAdapter.notifyDataSetChanged()

    }

    fun TextView.categorySetClicked(context: Context){
        this.background = ContextCompat.getDrawable(context, R.drawable.rec18_yellow)
        this.setTextColor(context.getColor(R.color.white))
    }
    fun TextView.categorySetUnClicked(context: Context){
        this.background = null
        this.setTextColor(context.getColor(R.color.grey))
    }



}