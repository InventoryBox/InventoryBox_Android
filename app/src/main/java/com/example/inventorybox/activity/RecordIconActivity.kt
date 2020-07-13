package com.example.inventorybox.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordIconAdapter
import com.example.inventorybox.data.RecordIconData
import kotlinx.android.synthetic.main.activity_category_edit.img_back
import kotlinx.android.synthetic.main.activity_icon_setting.*

class RecordIconActivity : AppCompatActivity(){
    val recordIconAdapter = RecordIconAdapter(this)
    var datas = mutableListOf<RecordIconData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_setting)

        rv_icon.adapter = recordIconAdapter
        loadRecordIconDatas()

        img_back.setOnClickListener {
            finish()
        }
    }

    private fun loadRecordIconDatas(){
        datas.apply {
            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

            add(
                RecordIconData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg"
                )
            )

        }

        recordIconAdapter.datas = datas
        recordIconAdapter.notifyDataSetChanged()

    }
}