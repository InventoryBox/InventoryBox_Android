package com.example.inventorybox.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.acitivity_home_profile.*

class HomeProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitivity_home_profile)

        btn_back_home_profile.setOnClickListener {
            finish()
        }

        et_profile_nickname.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.rec9_grey_yellow_border)
                    btn_profile.setBackgroundResource(R.drawable.rec30_yellow)
                }
                else {
                    v.setBackgroundResource(R.drawable.rec9_grey)
                    btn_profile.setBackgroundResource(R.drawable.rec30_grey)
                }
            }
    }
}