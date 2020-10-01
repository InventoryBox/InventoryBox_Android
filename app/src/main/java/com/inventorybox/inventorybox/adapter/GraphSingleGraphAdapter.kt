package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.ItemInfo
import com.inventorybox.inventorybox.fragment.GraphDetail
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_graph_main_graph.view.*
import com.inventorybox.inventorybox.graph.*
import java.lang.Exception

class GraphSingleGraphAdapter(private val context: Context, val manager:FragmentManager): RecyclerView.Adapter<GraphSingleGraphViewHolder>() {

  	var datas: MutableList<ItemInfo> = mutableListOf()

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

              val bundle = Bundle()
              bundle.putString("item_name", datas[position].name)
              bundle.putInt("itemIdx", datas[position].itemIdx)
              fragment.arguments = bundle
//              transaction.replace(R.id.frame_layout, fragment, "graphDetail").commit()
              transaction.add(R.id.frame_layout, fragment, "graph")
              transaction.addToBackStack(null) //해당 transaction을 백스택에 저장
              transaction.commit() //transaction 실행

          }

          holder.bind(datas[position])
//          holder.itemView.item_main_graph_chart.drawSingleGraph(context, datas[position].datas, datas[position].count_noti)

          holder.itemView.setOnClickListener(clickListener)
          holder.itemView.item_main_graph_chart.setOnClickListener(clickListener)

      }
  }

class  GraphSingleGraphViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val img_icon = itemView.item_main_graph_icon
    val name_product = itemView.item_main_graph_name
    val count_noti = itemView.item_main_graph_count_noti
    val chart : BarChart = itemView.item_main_graph_chart

    fun bind(data : ItemInfo){
        Glide.with(itemView.context).load(data.iconImg).into(img_icon)
        name_product.text = data.name
        count_noti.text = data.alarmCnt.toString()
//        count_noti.text = "2"
//        Log.d("testtest",data.alarmCnt.toString())
        try{

            chart.drawSingleGraph(itemView.context, data.stocks, data.alarmCnt)
        }catch (e : Exception){}

//        chart.drawSingleGraph(itemView.context, data.stocks, 2)
    }
 }