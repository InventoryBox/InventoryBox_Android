package com.example.inventorybox.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.Graph5DaysData
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.data.RequestExchangeLikeStatus
import com.example.inventorybox.graph.draw5DaysGraph
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_home_orderlist.view.*
import net.cachapa.expandablelayout.ExpandableLayout

class HomeOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()
    var datas2 = mutableListOf<Graph5DaysData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_orderlist, parent, false)
        return HomeOrderViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeOrderViewHolder, position: Int) {
        holder.bind(datas[position])


        holder.btn_more.setOnClickListener {
            holder.more()
        }

    }

}

class HomeOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var flag = 0

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<TextView>(R.id.tv_rv_count_noti)
    val unit = itemView.findViewById<TextView>(R.id.tv_home_unit)

    val check_box = itemView.findViewById<CheckBox>(R.id.checkBox)
    val btn_more = itemView.findViewById<ImageButton>(R.id.btn_rv_more)
    val expandable = itemView.findViewById<ExpandableLayout>(R.id.expandable_layout)

    val chart : BarChart = itemView.home_item_main_graph_chart

    fun bind(homeData: HomeOrderData) {
        Glide.with(itemView).load(homeData.img).into(img)
        name.text = homeData.itemName
        count.text = homeData.memoCnt.toString()
        unit.text = homeData.unit

        //flag가 1로 되어있으면 체크 되어있게
        if (homeData.flag == 1) {
            check_box.isChecked = true
        }

        //val datas = arrayListOf<Int>(1,2,3,2,1)
        chart.draw5DaysGraph(
            itemView.context,
            homeData.stocksInfo,
            homeData.lastDay,
            homeData.alarmCnt
        )


        //체크박스 클릭 시 서버 반영
        check_box.setOnClickListener {
            if (check_box.isChecked){
                flag = 1
                RequestToServer.service.HomeCheck(
                    homeData.itemIdx, flag
                ).customEnqueue(
                    onSuccess = {
                        Log.d("checkbox", "체크 박스 성공")
                    },
                    onFail = {
                        Log.d("checkbox", "체크 박스 실패")
                    }
                )
            }
            else{
                flag = 0
                RequestToServer.service.HomeCheck(
                    homeData.itemIdx, flag
                ).customEnqueue(
                    onSuccess = {
                        Log.d("checkbox", "체크 박스 성공")
                    },
                    onFail = {
                        Log.d("checkbox", "체크 박스 실패")
                    }
                )
            }

        }
    }


    //expandable layout 이벤트
    fun more(){
        expandable.toggle()
    }

}