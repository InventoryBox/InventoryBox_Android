package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.ServiceTermData

class ServiceTermRVAdapter(private val context: Context): RecyclerView.Adapter<ServiceTermViewHolder>() {
    var datas = mutableListOf<ServiceTermData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceTermViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_service_term, parent, false)
        return ServiceTermViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ServiceTermViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}

class ServiceTermViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.tv_service_term_title)
    val content = itemView.findViewById<TextView>(R.id.tv_service_term_content)

    fun bind(data: ServiceTermData) {
        title.text = data.title
        content.text = data.content
    }
}