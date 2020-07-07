package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.inventorybox.R
import com.example.inventorybox.data.HomeOrderData

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
    }

}

class HomeOrderEditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val index = itemView.findViewById<ConstraintLayout>(R.id.rv_home_container)
    val img = itemView.findViewById<ImageView>(R.id.img_rv_product)
    val name = itemView.findViewById<TextView>(R.id.tv_rv_product)
    val count = itemView.findViewById<EditText>(R.id.tv_rv_count)

    val plus = itemView.findViewById<ImageView>(R.id.iv_plus)
    val minus = itemView.findViewById<ImageView>(R.id.iv_minus)

    val btn_more = itemView.findViewById<ImageButton>(R.id.btn_rv_more)

    fun bind(homeOrderData: HomeOrderData){
        Glide.with(itemView).load(homeOrderData.img).into(img)
        name.text = homeOrderData.name
        //count.text = homeOrderData.count 여기 앞에서 받은걸로 해야함
        //count.setText(homeOrderData.count)
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

    /*fun check(context: Context){
        if(check_box.isChecked) {
            Glide.with(context).load(R.drawable.home_ic_checked).into(today_check)
            //name.setText("test")
        }else{

        }
    }*/

}