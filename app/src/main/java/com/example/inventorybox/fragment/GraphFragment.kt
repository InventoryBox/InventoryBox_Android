package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Data.GraphSingleData
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
        val graph_adapter = GraphSingleGraphAdapter(view.context)
        graph_adapter.datas=datas_graph_single
        graph_rv_single_graph.adapter=graph_adapter


    }
    // only for test
    private fun createDatas(): MutableList<GraphSingleData> {
        return mutableListOf<GraphSingleData>(
            GraphSingleData(
                "우유",
                R.drawable.data_ic_milk,
                3,
                arrayListOf(1,2,3,2,5,6,7)
            ),
            GraphSingleData(
                "커피",
                R.drawable.data_ic_coffee,
                5,
                arrayListOf(-1,2,3,0,5,6,7)
            )
        )
    }
}






