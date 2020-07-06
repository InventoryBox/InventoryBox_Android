package com.example.inventorybox.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.Data.GraphSingleData
import com.example.inventorybox.MainActivity
import com.example.inventorybox.R
import com.example.inventorybox.fragment.GraphDetail
import com.example.inventorybox.fragment.HomeFragment
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_graph_main_graph.view.*
import com.example.inventorybox.graph.*

class GraphSingleGraphAdapter(private val context: Context, val manager:FragmentManager): RecyclerView.Adapter<GraphSingleGraphViewHolder>() {

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
          //클릭되면 세부 사항 fragment로 이동
          val clickListener  = View.OnClickListener{
//              val intent = Intent(it.context, GraphDetail::class.java)
//              intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//              it.context.startActivity(intent)
              val fragment = GraphDetail()
              val transaction = manager.beginTransaction()
              transaction.replace(R.id.frame_layout, fragment, "graphDetail").commit()

          }

          holder.bind(datas[position])
          holder.itemView.item_main_graph_chart.drawSingleGraph(context, datas[position].datas, datas[position].count_noti)

          holder.itemView.setOnClickListener(clickListener)
          holder.itemView.item_main_graph_chart.setOnClickListener(clickListener)




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