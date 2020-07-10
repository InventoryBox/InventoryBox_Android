package com.example.inventorybox.adapter

import android.content.Context
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
import com.example.inventorybox.data.GraphSingleData
import com.example.inventorybox.data.GraphSingleWeekData
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.fragment.onHomeCheckListener
import com.example.inventorybox.fragment.onMyChangeListener
import com.example.inventorybox.graph.draw5DaysGraph
import com.example.inventorybox.graph.drawSingleGraph
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_graph_detail_graph_weeks.view.*
import kotlinx.android.synthetic.main.item_graph_main_graph.view.*
import kotlinx.android.synthetic.main.item_home_orderlist.view.*
import net.cachapa.expandablelayout.ExpandableLayout

class HomeOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()
    var datas2 = mutableListOf<Graph5DaysData>()

    lateinit var listener : onHomeCheckListener

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
        holder.bind(datas[position], listener)

//        holder.bind2(datas2[position])
//        holder.itemView.home_item_main_graph_chart.draw5DaysGraph(context, datas2[position].datas, 2,3)

        holder.btn_more.setOnClickListener {
            holder.more()
        }
    }
    fun set_Listener(listener: onHomeCheckListener){
        this.listener = listener
    }

}

class HomeOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<TextView>(R.id.tv_rv_count_noti)
    val unit = itemView.findViewById<TextView>(R.id.tv_home_unit)

    val check_box = itemView.findViewById<CheckBox>(R.id.checkBox)
    val btn_more = itemView.findViewById<ImageButton>(R.id.btn_rv_more)
    val expandable = itemView.findViewById<ExpandableLayout>(R.id.expandable_layout)
    val today_check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)

    val chart : BarChart = itemView.home_item_main_graph_chart


    fun bind(homeOrderData: HomeOrderData, listener: onHomeCheckListener){
        Glide.with(itemView).load(homeOrderData.img).into(img)
        name.text = homeOrderData.name
        count.text = homeOrderData.count.toString()
        unit.text = homeOrderData.unit

        val datas = arrayListOf<Int>(1,2,3,2,1)
        chart.draw5DaysGraph(itemView.context, datas, 4, 3)

        check_box.setOnClickListener {
            listener.onChange(adapterPosition, false)
        }
    }

    fun bind2(data : Graph5DaysData){
        val datas = arrayListOf<Int>(-1,-1,1,2,3,2,1)
        chart.draw5DaysGraph(itemView.context, datas, 2, 3)
//        chart.draw5DaysGraph(itemView.context, data.datas, 2,data.count_noti)
    }

    //expandable layout 이벤트
    fun more(){
        expandable.toggle()
    }

}