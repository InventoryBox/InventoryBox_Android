package com.example.inventorybox.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_service_privacy.*

class ServicePrivacyActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_privacy)

        btn_back_service_privacy.setOnClickListener {
            finish()
        }

        tv_service_terms_privacy.text = Html.fromHtml(getString(R.string.terms_personal), Html.FROM_HTML_MODE_COMPACT)
    }

}