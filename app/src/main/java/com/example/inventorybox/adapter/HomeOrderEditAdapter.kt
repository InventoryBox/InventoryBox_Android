package com.example.inventorybox.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.graph.draw5DaysGraph
import com.github.mikephil.charting.charts.BarChart
import kotlinx.android.synthetic.main.item_home_edit_memo.view.*
import kotlinx.android.synthetic.main.item_home_orderlist.view.*
import net.cachapa.expandablelayout.ExpandableLayout

class HomeOrderEditAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderEditViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOrderEditViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_edit_memo, parent, false)
        return HomeOrderEditViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeOrderEditViewHolder, position: Int) {
        holder.bind(datas[position])

        /*holder.check_box.setOnCheckedChangeListener { compoundButton, b ->
           // holder.check(holder.itemView.context)
            if(holder.check_box.isChecked) {
                Glide.with(context).load(R.drawable.home_ic_checked).into(holder.itemView.iv_home_today_check)
                //name.setText("test")
            }else{

            }
        }*/

        holder.plus.setOnClickListener {
            holder.increment()
        }

        holder.minus.setOnClickListener {
            holder.decrement()
        }

        holder.btn_more.setOnClickListener {
            Log.d("###############", "########")
            holder.more()
        }
    }

}

class HomeOrderEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<EditText>(R.id.tv_rv_count_noti)

    val plus = itemView.findViewById<ImageView>(R.id.iv_plus)
    val minus = itemView.findViewById<ImageView>(R.id.iv_minus)

    val btn_more = itemView.findViewById<ImageButton>(R.id.edit_btn_rv_more)
    val expandable = itemView.findViewById<ExpandableLayout>(R.id.edit_expandable_layout)

    val chart : BarChart = itemView.item_main_graph_chart_memo

    fun bind(homeData: HomeOrderData){
        Glide.with(itemView).load(homeData.img).into(img)
        name.text = homeData.itemName
        //count.text = homeData.memoCnt
        //count.setText(homeOrderData.count)

        chart.draw5DaysGraph(itemView.context, homeData.stocksInfo, 4, homeData.alarmCnt)
    }

    //+버튼 눌렀을 때 숫자 증가
    fun increment(){
        var current = Integer.parseInt(count.getText().toString())
        count.setText((++current).toString())
    }

    //-버튼 눌렀을 때 숫자 감소
    fun decrement(){
        var current = Integer.parseInt(count.getText().toString())

        //0보다 작을수 없음
        if(current <= 0){
            current == 0
        }else{
            count.setText((--current).toString())
        }
    }

    //expandable layout 이벤트
    fun more(){
        expandable.toggle()
    }

    /*fun check(context: Context){
        if(check_box.isChecked) {
            Glide.with(context).load(R.drawable.home_ic_checked).into(today_check)
            //name.setText("test")
        }else{

        }
    }*/

}
