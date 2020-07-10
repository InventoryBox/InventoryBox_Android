package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.R
import com.example.inventorybox.fragment.onMyChangeListener
import kotlinx.android.synthetic.main.fragment_graph_detail.*

class HomeTodayOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeTodayOrderViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTodayOrderViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_today_order, parent, false)
        return HomeTodayOrderViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: HomeTodayOrderViewHolder, position: Int) {
        holder.bind(datas[position])

    }

}

class HomeTodayOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_today_container)
    val name = itemView.findViewById<TextView>(R.id.tv_home_today)
    //val check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)
    val recycler = itemView.findViewById<RecyclerView>(R.id.rv_home_today_order)
    val check_box = itemView.findViewById<CheckBox>(R.id.checkBox)
    val today_check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)

    //발주 확인 아이템을 오늘 발주할 재고 확인 메모에 표시
    fun bind(homeOrderData: HomeOrderData){
        name.text = homeOrderData.name
    }

}
