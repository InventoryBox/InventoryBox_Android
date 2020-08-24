package com.example.inventorybox.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.acitivity_home_profile.*
import kotlinx.android.synthetic.main.acitivity_home_profile.btn_profile
import kotlinx.android.synthetic.main.activity_home_personal.*

class HomePersonalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_personal)

        btn_back_home_personal.setOnClickListener {
            finish()
        }

        personal_et_bussiness.onFocusChangeListener =
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

        personal_et_store.onFocusChangeListener =
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

        personal_et_store_location.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.underline_yellow)
                    btn_profile.setBackgroundResource(R.drawable.rec30_yellow)
                }
                else {
                    v.setBackgroundResource(R.drawable.underline_grey)
                    btn_profile.setBackgroundResource(R.drawable.rec30_grey)
                }
            }


        personal_et_tel.onFocusChangeListener =
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