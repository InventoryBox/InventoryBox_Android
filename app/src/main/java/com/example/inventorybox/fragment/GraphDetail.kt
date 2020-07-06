package com.example.inventorybox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.fragment_graph_detail.*

class GraphDetail : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        graph_detail_btn_back.setOnClickListener {
//            val fragment = GraphDetail()
//            val transaction = fragmentManager!!.beginTransaction()
//            transaction.replace(R.id.frame_layout, fragment, "graphDetail").commit()
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_graph_detail, container, false)
    }


}