package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Adpater.GraphCalendarAdapter
import com.example.inventorybox.Adpater.GraphCategoryRVAdapter
import com.example.inventorybox.R
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
        graph_rv_cate.setOnClickListener {

        }

    }
}






