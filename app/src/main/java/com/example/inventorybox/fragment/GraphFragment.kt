package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import com.example.inventorybox.data.GraphSingleData
import com.example.inventorybox.adapter.GraphCalendarAdapter
import com.example.inventorybox.adapter.GraphCategoryRVAdapter
import com.example.inventorybox.R
import com.example.inventorybox.adapter.GraphSingleGraphAdapter
import kotlinx.android.synthetic.main.fragment_graph.*


class GraphFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val datas_cate= mutableListOf<String>("전체","치킨류","유제품","액체류","피자류","고기류")

        val rv_adapter=GraphCalendarAdapter(view.context)
       graph_rv_calendar.adapter=rv_adapter
        // 상단 카테고리
        val category_adapter = GraphCategoryRVAdapter(view.context)
        category_adapter.datas = datas_cate
        graph_rv_cate.adapter = category_adapter

        val datas_graph_single = createDatas()
        val graph_adapter = GraphSingleGraphAdapter(view.context, fragmentManager!!)
        graph_adapter.datas=datas_graph_single
        graph_rv_single_graph.adapter=graph_adapter
//        graph_rv_single_graph.isNestedScrollingEnabled=false
        graph_rv_single_graph.overScrollMode=View.OVER_SCROLL_NEVER

        //floating button 누르면 맨위로
        graph_main_btn_float.setOnClickListener {
            graph_main_scroll.scrollTo(0,0)
        }


    }
    // only for test
    private fun createDatas(): MutableList<GraphSingleData> {
        return mutableListOf<GraphSingleData>(
            GraphSingleData(
                "우유",
                R.drawable.data_ic_milk,
                3,
                arrayListOf(1,2,3,0,5,6,7)
            ),
            GraphSingleData(
                "원두",
                R.drawable.data_ic_coffee,
                5,
                arrayListOf(-1,4,10,11,9,-1,-1)
            ),
            GraphSingleData(
                "컵 12oz",
                R.drawable.data_ic_cup,
                5,
                arrayListOf(-1,11,2,2,3,-1,-1)
            ),
            GraphSingleData(
                "원두",
                R.drawable.data_ic_coffee,
                5,
                arrayListOf(-1,4,10,11,9,-1,-1)
            ),
            GraphSingleData(
                "원두",
                R.drawable.data_ic_coffee,
                5,
                arrayListOf(-1,4,10,11,9,-1,-1)
            )

        )
    }
}






