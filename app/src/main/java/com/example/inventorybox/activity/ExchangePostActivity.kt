package com.example.inventorybox.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_exchange_post.*

class ExchangePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_post)

        // 뒤로가기 버튼 눌리면 activity 나가기
        btn_finish.setOnClickListener {
            finish()
        }

    }
}