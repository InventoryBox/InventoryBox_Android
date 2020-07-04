package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Adpater.GraphCalendarAdapter
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

        val rv_adapter=GraphCalendarAdapter(view.context)
        graph_rv_calendar.adapter=rv_adapter
        //rv 스크롤 없애기

    }
}






