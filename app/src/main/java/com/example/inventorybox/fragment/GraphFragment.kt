 package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.DB.SharedPreferenceController
import com.example.inventorybox.adapter.GraphCalendarAdapter
import com.example.inventorybox.adapter.GraphCategoryRVAdapter
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphSingleGraphAdapter
import com.example.inventorybox.data.CategoryInfo
import com.example.inventorybox.data.ItemInfo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_graph.*
import java.text.SimpleDateFormat
import java.util.*


 class GraphFragment : Fragment() {

     val cal : Calendar = Calendar.getInstance()
     var datas_cate =  mutableListOf<CategoryInfo>()
     var datas_cal : MutableList<String>  = mutableListOf()
     var datas_graph : MutableList<ItemInfo> = mutableListOf()
     var sorted_datas_graph : MutableList<ItemInfo> = mutableListOf()

     // 상단 캘린더
     lateinit var rv_adapter: GraphCalendarAdapter
     lateinit var category_adapter : GraphCategoryRVAdapter
     lateinit var graph_adapter : GraphSingleGraphAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 서버에서 데이터 받아오기
        requestData()

        //현재 날짜 받아와서 캘린더 뷰 띄우기
        val format = SimpleDateFormat("MM")
//        cal_month.text=cal.get(Calendar.MONTH).toString()
        cal_month.text=format.format(cal.time)
        cal_week.text=when(cal.get(Calendar.WEEK_OF_MONTH)){
            0-> "첫째주"
            1-> "둘째주"
            2-> "셋째주"
            3-> "넷째주"
            else -> "다섯째주"
        }

        rv_adapter=GraphCalendarAdapter(view.context)
        // 현재요일 일요일 0 토요일 6
        rv_adapter.today = cal.get(Calendar.DAY_OF_WEEK)-1
//        Log.d("graphFragment", "${rv_adapter.today}")
        graph_rv_calendar.adapter=rv_adapter

        let{


            graph_adapter = GraphSingleGraphAdapter(view.context, fragmentManager!!)
            graph_rv_single_graph.adapter=graph_adapter
            graph_rv_single_graph.overScrollMode=View.OVER_SCROLL_NEVER
        }


        // 카테고리 sort
        val categoryListener = object : CategoryClickListener{
            override fun onClick(category_idx: Int) {
                if(category_idx>1){
                    sorted_datas_graph = datas_graph.filter {
                        it.categoryIdx==category_idx
                    }.toMutableList()
//                    for(item in datas_graph){
//                        if(item.categoryIdx==category_idx){
//                            sorted_datas_graph.add(item)
//                        }
//                    }
                    graph_adapter.datas = sorted_datas_graph
                }else{
                    graph_adapter.datas = datas_graph
                }
                graph_adapter.notifyDataSetChanged()
            }
        }
        // 상단 카테고리
        category_adapter = GraphCategoryRVAdapter(view.context)
        category_adapter.datas = datas_cate
        category_adapter.set_listener(categoryListener)
        graph_rv_cate.adapter = category_adapter




        //floating button 누르면 맨위로
        graph_main_btn_float.setOnClickListener {
            graph_main_scroll.smoothScrollTo(0,0)
        }


    }
     fun requestData(){


         RequestToServer.service.requestGraphMainData(
             SharedPreferenceController.getUserToken(context!!)
         ).customEnqueue(
             onSuccess = {
//                datas_cal = it.data.thisWeekDates.toMutableList()
                 if(!it.data.thisWeekDates.isNullOrEmpty()){
                     for(data in it.data.thisWeekDates){
                         datas_cal.add(data)
                     }
                 }
                 rv_adapter.datas = datas_cal
//                graph_rv_calendar.adapter=rv_adapter
                 rv_adapter.notifyDataSetChanged()

//                datas_cate = it.data.categoryInfo.toMutableList()
                 for(data in it.data.categoryInfo){
                 datas_cate.add(data)
             }
                 category_adapter.datas = datas_cate
                 category_adapter.notifyDataSetChanged()


//                datas_graph = it.data.itemInfo.toMutableList()
                 for(data in it.data.itemInfo){
                     datas_graph.add(data)
                 }


                 graph_adapter.datas = datas_graph
                 graph_adapter.notifyDataSetChanged()
//                 for(data in it.thisWeekDates){
//                    datas_cal.add(data)
//                }
//
//                datas_cate = it.categoryInfo.toMutableList()
//                datas_graph = it.itemInfo.toMutableList()
//                Log.d("network_success","$datas_graph")

             }
         )

     }

     interface CategoryClickListener{
         fun onClick(category_idx : Int)
     }
}






