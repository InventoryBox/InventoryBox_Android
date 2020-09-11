package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import net.cachapa.expandablelayout.ExpandableLayout

class SettingInstructionsAdapter(private val context: Context) : RecyclerView.Adapter<SettingInstructionsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingInstructionsViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_setting_instructions, parent, false)
        return SettingInstructionsViewHolder(
            view
        )
    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: SettingInstructionsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}

class SettingInstructionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val expandable = itemView.findViewById<ExpandableLayout>(R.id.btn_instructions_more)

    //expandable layout
    fun expandable(){
        expandable.toggle()
    }


}