package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)


        btn_back_service.setOnClickListener {
            finish()
        }

        service_term.setOnClickListener {
            startActivity(Intent(this, ServiceTermActivity::class.java))
        }

        service_privacy.setOnClickListener {
            startActivity(Intent(this, ServicePrivacyActivity::class.java))
        }
    }
}