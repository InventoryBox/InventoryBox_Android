package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.data.Personal
import com.example.inventorybox.data.RequestPersonal
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.acitivity_home_profile.*
import kotlinx.android.synthetic.main.acitivity_home_profile.btn_profile
import kotlinx.android.synthetic.main.activity_home_personal.*
import kotlinx.android.synthetic.main.fragment_exchange.*

class HomePersonalActivity : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_personal)

        btn_back_home_personal.setOnClickListener {
            finish()
        }

        //개인 정보 가져오기
        personalResponse()

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

        personal_et_store_location.setOnClickListener {
            val intent = Intent(this, ExchangeSetLocation::class.java)
            startActivity(intent)
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

        btn_profile.setOnClickListener {
            val changed_rep_name = personal_et_bussiness.text.toString()
            val changed_co_name = personal_et_store.text.toString()
            val changed_phone = personal_et_tel.text.toString()
            val changed_location = personal_et_store_location.text.toString()


            requestToServer.service.requestPersonal(
                getString(R.string.test_token),
                RequestPersonal(
                    repName = changed_rep_name,
                    coName = changed_co_name,
                    phone = changed_phone,
                    location = changed_location
                )
            ).customEnqueue(
                onSuccess = {
                    Log.d("personal", "개인 정보 변경 성공")
                    finish()
                }
            )
        }

    }

    override fun onStart() {
        super.onStart()
        RequestToServer.service.requestExchangeHomeData(
            getString(R.string.test_token),
            1
        ).customEnqueue(
            onSuccess = {
                personal_et_store_location.text = it.data.addressInfo
            }
        )
    }

    private fun personalResponse() {
        requestToServer.service.getPersonal(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                Log.d("personal", "개인 정보 가져오기 성공")

                personal_et_bussiness.setText(it.data.result[0].repName)
                personal_et_store.setText(it.data.result[0].coName)
                personal_et_tel.setText(it.data.result[0].phoneNumber)

            }
        )
    }
}