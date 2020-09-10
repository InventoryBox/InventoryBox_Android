package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ServicePrivacyRVAdapter
import com.example.inventorybox.data.ServiceTermData
import kotlinx.android.synthetic.main.activity_service_privacy.*
import kotlinx.android.synthetic.main.activity_service_term.*

class ServicePrivacyActivity : AppCompatActivity() {

    lateinit var servicePrivacyRVAdapter: ServicePrivacyRVAdapter
    var datas = mutableListOf<ServiceTermData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_privacy)

        btn_back_service_privacy.setOnClickListener {
            finish()
        }

        servicePrivacyRVAdapter = ServicePrivacyRVAdapter(this)
        rv_service_privacy.adapter = servicePrivacyRVAdapter

        loadDatas()
    }

    private fun loadDatas() {
        datas.apply {
            add(
                ServiceTermData(
                    title = "제 1조 (목적)",
                    content = getString(R.string.service_term_content)
                ))
            add(
                ServiceTermData(
                    title = "제 2조 (목적)",
                    content = getString(R.string.service_term_content)
                ))
            add(
                ServiceTermData(
                    title = "제 3조 (목적)",
                    content = getString(R.string.service_term_content)
                ))
        }
        servicePrivacyRVAdapter.datas = datas
        servicePrivacyRVAdapter.notifyDataSetChanged()
    }
}