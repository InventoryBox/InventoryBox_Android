package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.ServiceTermRVAdapter
import com.example.inventorybox.data.ServiceTermData
import kotlinx.android.synthetic.main.activity_service_term.*

class ServiceTermActivity : AppCompatActivity() {

    lateinit var serviceTermAdapter: ServiceTermRVAdapter

    var datas = mutableListOf<ServiceTermData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_term)

        btn_back_service_term.setOnClickListener {
            finish()
        }

        serviceTermAdapter = ServiceTermRVAdapter(this)
        rv_service_term.adapter = serviceTermAdapter


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
        serviceTermAdapter.datas = datas
        serviceTermAdapter.notifyDataSetChanged()
    }
}