package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordIconAdapter
import com.example.inventorybox.data.RecordAddIconInfo
import com.example.inventorybox.data.RecordIconData
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.custonEnqueue
import kotlinx.android.synthetic.main.activity_category_edit.img_back
import kotlinx.android.synthetic.main.activity_icon_setting.*

class RecordIconActivity : AppCompatActivity(){
    val requestToServer = RequestToServer
    var datas_icon = mutableListOf<RecordAddIconInfo>()
    lateinit var icon_adapter : RecordIconAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_setting)

        icon_adapter = RecordIconAdapter(this)
        rv_icon.adapter = icon_adapter
        RecordIconResponse()

        img_back.setOnClickListener {
            finish()
        }
    }

    private fun RecordIconResponse(){
        requestToServer.service.getRecordAddResponse(
            getString(R.string.test_token)
        ).custonEnqueue(
            onSuccess = {
                for (data in it.data.iconInfo){
                    datas_icon.add(data)
                }
                icon_adapter.datas= datas_icon
                icon_adapter.notifyDataSetChanged()
            }
        )
    }
}