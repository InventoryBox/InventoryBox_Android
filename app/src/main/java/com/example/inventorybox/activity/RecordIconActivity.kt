package com.example.inventorybox.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordIconAdapter
import com.example.inventorybox.data.RecordAddIconInfo
import com.example.inventorybox.data.RecordIconData
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_category_edit.img_back
import kotlinx.android.synthetic.main.activity_icon_setting.*
import kotlinx.android.synthetic.main.item_record_icon.*

class RecordIconActivity : AppCompatActivity(){
    val requestToServer = RequestToServer
    var datas_icon = mutableListOf<RecordAddIconInfo>()
    lateinit var icon_adapter : RecordIconAdapter

    val REQUEST_IMG = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_setting)

        val listener = object : IconClickListener{
            override fun onClick(icon_idx: Int, icon_url: String) {
                val intent = Intent()
                intent.putExtra("icon_idx", icon_idx)
                intent.putExtra("icon_url", icon_url)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

        icon_adapter = RecordIconAdapter(this)
        icon_adapter.listener = listener
        rv_icon.adapter = icon_adapter

        RecordIconResponse()

        img_back.setOnClickListener {
            finish()
        }
    }

    private fun RecordIconResponse(){
        requestToServer.service.getRecordAddResponse(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                for (data in it.data.iconInfo){
                    datas_icon.add(data)
                }
                icon_adapter.datas= datas_icon
                icon_adapter.notifyDataSetChanged()
            }
        )
    }

    interface IconClickListener{
        fun onClick(icon_idx : Int, icon_url : String)
    }
}