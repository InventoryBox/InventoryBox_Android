package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.Data.GraphSingleData
import com.example.inventorybox.R
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_graph_main_graph.view.*
import com.example.inventorybox.graph.*

class GraphSingleGraphAdapter(private val context: Context): RecyclerView.Adapter<GraphSingleGraphViewHolder>() {

  	var datas: MutableList<GraphSingleData> = mutableListOf()

  	// xml file을 inflate한 후 viewHolder를 만든다.
      override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  GraphSingleGraphViewHolder{
  	    val view = LayoutInflater.from(context).inflate(R.layout.item_graph_main_graph, parent,false)
  		return GraphSingleGraphViewHolder(view)
      }

      override fun getItemCount(): Int {
  		 return datas.size
}
  	  // viewholder의 항목을 구성하기 위해 호출된다.
      override fun onBindViewHolder(holder: GraphSingleGraphViewHolder, position: Int) {
          holder.bind(datas[position])
          holder.itemView.item_main_graph_chart.drawSingleGraph(context, datas[position].datas, datas[position].count_noti)
      }
  }

class  GraphSingleGraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val img_icon = itemView.item_main_graph_icon
    val name_product = itemView.item_main_graph_name
    val count_noti = itemView.item_main_graph_count_noti
    val chart : BarChart = itemView.item_main_graph_chart

    fun bind(data : GraphSingleData){
        Glide.with(itemView.context).load(data.icon).into(img_icon)
        name_product.text = data.name_product
        count_noti.text = data.count_noti.toString()
        chart.drawSingleGraph(itemView.context, data.datas, data.count_noti)
    }
 }