package com.example.inventorybox.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.inventorybox.R
import com.example.inventorybox.etc.CustomDialog
import kotlinx.android.synthetic.main.activity_exchange_item_detail.*

class ExchangeItemDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_item_detail)
//
//        // 상세 설명 페이지 밑줄
//        //test
//        val s = getString(R.string.test)
//        val span_s = SpannableString(s)
//        span_s.setSpan(UnderlineSpan(),0, span_s.length, 0)
//        tv_item_description.setText(span_s)

        btn_edit.setOnClickListener {
//            val view = LayoutInflater.from(this).inflate(R.layout.layout_custom_dialog, null)
//            val builder = AlertDialog.Builder(this)
//                .setView(view)
//
//            val dialog = builder.show()



        }
    }
}