package com.example.inventorybox.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.R
import com.example.inventorybox.data.HomeOrderData
import com.example.inventorybox.data.ServicePrivacyData
import org.w3c.dom.Text

class ServiceLicenseRVAdapter(private val context: Context): RecyclerView.Adapter<ServiceLicenseViewHolder>() {
    var datas = mutableListOf<ServicePrivacyData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceLicenseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_service_license, parent, false)
        return ServiceLicenseViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ServiceLicenseViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}

class ServiceLicenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.tv_license_title)
    val content = itemView.findViewById<TextView>(R.id.tv_license_content)

    fun bind(data: ServicePrivacyData) {
        title.text = data.title
        content.text = data.content
    }

}