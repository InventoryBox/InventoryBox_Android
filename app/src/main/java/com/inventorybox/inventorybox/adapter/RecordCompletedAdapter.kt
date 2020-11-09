package com.inventorybox.inventorybox.Adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.ViewHolder.RecordCompletedVH
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RecordHomeItemInfo

class RecordCompletedAdapter(private val context: Context) : RecyclerView.Adapter<RecordCompletedVH>() {
    var datas = mutableListOf<RecordHomeItemInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordCompletedVH {
        val view = LayoutInflater.from(context).inflate(R.layout.item_record_completedlist, parent, false)
        return RecordCompletedVH(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RecordCompletedVH, position: Int) {
        holder.bind(datas[position])

    }

}