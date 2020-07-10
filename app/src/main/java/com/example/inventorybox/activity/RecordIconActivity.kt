package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_category_edit.*

class RecordIconActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_setting)

        img_back.setOnClickListener {
            finish()
        }
    }
}