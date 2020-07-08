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
import com.example.inventorybox.data.HomeOrderData
import net.cachapa.expandablelayout.ExpandableLayout

class HomeOrderAdapter(private val context: Context) : RecyclerView.Adapter<HomeOrderViewHolder>() {
    var datas = mutableListOf<HomeOrderData>()

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

        /*holder.check_box.setOnCheckedChangeListener { compoundButton, b ->
           // holder.check(holder.itemView.context)
            if(holder.check_box.isChecked) {
                Glide.with(context).load(R.drawable.home_ic_checked).into(holder.itemView.iv_home_today_check)
                //name.setText("test")
            }else{

            }
        }*/
    }

}

class HomeOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<TextView>(R.id.tv_rv_count_noti)

    val check_box = itemView.findViewById<CheckBox>(R.id.checkBox)
    val btn_more = itemView.findViewById<ImageButton>(R.id.btn_rv_more)
    val expandable = itemView.findViewById<ExpandableLayout>(R.id.expandable_layout)
    val today_check = itemView.findViewById<ImageView>(R.id.iv_home_today_check)

    fun bind(homeOrderData: HomeOrderData){
        Glide.with(itemView).load(homeOrderData.img).into(img)
        name.text = homeOrderData.name
        count.text = homeOrderData.count.toString()
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
