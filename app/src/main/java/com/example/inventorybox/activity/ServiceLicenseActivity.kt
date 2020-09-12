package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ServiceLicenseRVAdapter
import com.example.inventorybox.data.ServicePrivacyData
import com.example.inventorybox.data.ServiceTermData
import com.example.inventorybox.network.RequestToServer
import kotlinx.android.synthetic.main.activity_service_license.*
import kotlinx.android.synthetic.main.activity_service_term.*

class ServiceLicenseActivity : AppCompatActivity() {

    lateinit var serviceLicenseRVAdapter: ServiceLicenseRVAdapter
    var datas = mutableListOf<ServicePrivacyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_license)


        btn_back_service_license.setOnClickListener {
            finish()
        }

        serviceLicenseRVAdapter = ServiceLicenseRVAdapter(this)
        rv_service_license.adapter = serviceLicenseRVAdapter

        loadDatas()
    }

    private fun loadDatas() {
        datas.apply {
            add(
                ServicePrivacyData(
                    title = "SDWebImage",
                    content = getString(R.string.service_license_content)
                )
            )
            add(
                ServicePrivacyData(
                    title = "SDWebImage2",
                    content = getString(R.string.service_license_content)
                )
            )
            add(
                ServicePrivacyData(
                    title = "SDWebImage3",
                    content = getString(R.string.service_license_content)
                )
            )
        }
        serviceLicenseRVAdapter.datas = datas
        serviceLicenseRVAdapter.notifyDataSetChanged()
    }
}