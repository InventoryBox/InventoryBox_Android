package com.inventorybox.inventorybox.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.adapter.RecordIconAdapter
import com.inventorybox.inventorybox.data.RecordAddIconInfo
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_icon_setting.*

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
            SharedPreferenceController.getUserToken(this)
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
