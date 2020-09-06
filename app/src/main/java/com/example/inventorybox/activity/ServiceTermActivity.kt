package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_service_term.*

class ServiceTermActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_term)

        btn_back_service_term.setOnClickListener {
            finish()
        }

    }
}