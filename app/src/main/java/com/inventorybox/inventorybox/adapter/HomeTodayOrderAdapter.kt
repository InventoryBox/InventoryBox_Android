package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.HomeOrderData

class HomeTodayOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeTodayOrderViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()

    private var selected_pos = 0


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
    //var isClicked =true
    var isChecked = 1

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_today_container)
    val name = itemView.findViewById<TextView>(R.id.tv_home_today)
    val check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)


//    //체크 클릭 시 이벤트
//    init {
//        check.setOnClickListener {
//            if(isClicked){
//                check.setImageResource(R.drawable.home_ic_checked)
//                isClicked = false
//            }else{
//                check.setImageResource(R.drawable.home_ic_notyet)
//                isClicked = true
//            }
//        }
//    }

    //발주 확인 아이템을 오늘 발주할 재고 확인 메모에 표시
    fun bind(homeData: HomeOrderData){
        name.text = homeData.itemName

        //체크 flag
        if(homeData.flag == 1){
            check.setImageResource(R.drawable.home_ic_checked)
            isChecked = 0
        }else{
            check.setImageResource(R.drawable.home_ic_notyet)
            isChecked = 1
        }
    }
}
