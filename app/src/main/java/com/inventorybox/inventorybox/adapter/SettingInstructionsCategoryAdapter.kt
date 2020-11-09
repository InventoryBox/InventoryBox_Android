package com.inventorybox.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.activity.SettingInstructionsActivity
import com.inventorybox.inventorybox.data.SettingInstructionsCategoryData
import kotlinx.android.synthetic.main.item_record_category.view.*
import kotlin.math.roundToInt

class SettingInstructionsCategoryAdapter(private val context: Context) : RecyclerView.Adapter<SettingInstructionsCategoryViewHolder>() {
    var datas = mutableListOf<SettingInstructionsCategoryData>()

    lateinit var listener : SettingInstructionsActivity.CategoryClickListener
    var selected_pos = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingInstructionsCategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_graph_category, parent, false)
        return SettingInstructionsCategoryViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SettingInstructionsCategoryViewHolder, position: Int) {
        holder.bind(datas[position])

        //클릭 시 카테고리 색 변화
        if(selected_pos==position){
            holder.set_selected()
        }else{
            holder.set_unselected()
        }

        holder.itemView.setOnClickListener {
            selected_pos=position
            listener.onClick(datas[position].index)
            notifyDataSetChanged()
        }
        if(position==0){
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginStart = dpToPx(holder.itemView.context, 16)
            params.marginEnd = dpToPx(holder.itemView.context, 8)
            holder.itemView.layoutParams = params
        }else if(position==itemCount-1) {
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )
//            params.marginStart = dpToPx(holder.itemView.context, 8)
            params.marginEnd = dpToPx(holder.itemView.context, 16)
            holder.itemView.layoutParams = params
        } else {
            val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.marginEnd = dpToPx(holder.itemView.context, 8)
            params.marginStart = dpToPx(holder.itemView.context, 0)
            holder.itemView.layoutParams = params
        }

    }
    fun dpToPx(context: Context, dp: Int): Int {
        val density: Float = context.resources
                .displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

}

class SettingInstructionsCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val category : TextView = itemView.findViewById(R.id.tv_category_name)

    fun bind (myData : SettingInstructionsCategoryData){
        category.text = myData.tv_category_name
    }

    //selected일 때 변화
    fun set_selected(){
        itemView.setBackgroundResource(R.drawable.graph_rec20_dark_grey)
        itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
    }
    //unselect일 때의 변화
    fun set_unselected(){
        itemView.setBackgroundResource(R.drawable.graph_rec20_grey_blank)
        itemView.tv_category_name.setTextColor(ContextCompat.getColor(itemView.context, R.color.darkgrey))
    }
}