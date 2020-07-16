package com.example.inventorybox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_exchange_show_my_post.*

class ExchangeShowMyPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange_show_my_post)

        Glide.with(this).load(R.drawable.write).into(iv_exchange_show_mine)

    }

}