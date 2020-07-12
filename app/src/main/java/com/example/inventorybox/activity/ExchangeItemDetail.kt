package com.example.inventorybox.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.inventorybox.R
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
    }
}