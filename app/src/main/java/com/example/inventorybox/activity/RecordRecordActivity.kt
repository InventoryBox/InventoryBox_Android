package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordAddAdapter
import com.example.inventorybox.data.RecordAddData
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.android.synthetic.main.fragment_record.*

class RecordRecordActivity : AppCompatActivity() {

    val recordAddAdapter= RecordAddAdapter(this)
    var datas = mutableListOf<RecordAddData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        rv_record_add.adapter = recordAddAdapter
        loadRecordAddDatas()

        ll_up.setOnClickListener {
            scrollview_record_add.smoothScrollTo(0, 0)
        }

    }

    private fun loadRecordAddDatas(){
        datas.apply {
            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 0
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 0
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 0
                )
            )

            add(
                RecordAddData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    input_count = 0
                )
            )
        }

        recordAddAdapter.datas = datas
        recordAddAdapter.notifyDataSetChanged()

    }



}