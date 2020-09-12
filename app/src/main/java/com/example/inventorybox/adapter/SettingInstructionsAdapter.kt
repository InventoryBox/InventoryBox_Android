package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.SettingInstructionsData
import net.cachapa.expandablelayout.ExpandableLayout

class SettingInstructionsAdapter(private val context: Context) : RecyclerView.Adapter<SettingInstructionsViewHolder>() {
    var datas = mutableListOf<SettingInstructionsData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingInstructionsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_instructions, parent, false)
        return SettingInstructionsViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: SettingInstructionsViewHolder, position: Int) {
        holder.bind(datas[position])

        holder.icon_more.setOnClickListener {
            holder.expandable()
        }

    }

}

class SettingInstructionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val question  = itemView.findViewById<TextView>(R.id.tv_instruction_Q)
    val answer  = itemView.findViewById<TextView>(R.id.tv_instruction_A)
    val icon_more  = itemView.findViewById<ImageView>(R.id.btn_instructions_more)

    val expandable_layout = itemView.findViewById<ExpandableLayout>(R.id.ins_expandable_layout)

    fun bind (myData : SettingInstructionsData){
        question.text = myData.tv_instruction_Q
        answer.text = myData.tv_instruction_A
    }

    //expandable layout
    fun expandable(){
        expandable_layout.toggle()
    }


}