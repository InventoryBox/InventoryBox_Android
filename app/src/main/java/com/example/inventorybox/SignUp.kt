package com.example.inventorybox

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.inventorybox.activity.HomeSettingsActivity
import com.example.inventorybox.activity.LoginActivity
import com.example.inventorybox.activity.MainActivity
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.NetworkService
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.fragment_graph_detail.*

class SignUp : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //포커스 되면 밑줄 색상 변화
        isDataValid()

        //회원가입 다음 버튼 이벤트
        //로그인으로
        signup_btn_next.setOnClickListener {
            val signup_email: String = editTextTextEmailAddress2.text.toString()
            val signup_password: String = editTextNumber2.text.toString()


            /*if (ifUserInfoValid(signup_email, signup_password)){
                postSignupResponse(signup_email, signup_password)
            }*/

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

    //
    /*private fun ifUserInfoValid(u_email: String, u_password: String): Boolean{

    }*/


    //포커스 되면 밑줄, 버튼 색상 변화
    fun isDataValid() {
        editTextTextEmailAddress2.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus || editTextTextEmailAddress2.text.toString() != ""){
                    v.setBackgroundResource(R.drawable.underline_yellow)
                    signup_btn_1.setBackgroundResource(R.drawable.signup_btn_yellow)
                }
                else {
                    v.setBackgroundResource(R.drawable.underline_grey)
                    signup_btn_1.setBackgroundResource(R.drawable.signup_btn_middlegrey)
                }
            }
        editTextNumber2.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || editTextNumber2.text.toString() != ""){
                v.setBackgroundResource(R.drawable.underline_yellow)
                signup_btn_ok.setBackgroundResource(R.drawable.signup_btn_yellow)
            }
            else {
                v.setBackgroundResource(R.drawable.underline_grey)
                signup_btn_ok.setBackgroundResource(R.drawable.signup_btn_middlegrey)
            }
        }
        editTextTextPassword3.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || editTextTextPassword3.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_grey)
        }
        editTextTextPassword4.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus || editTextTextPassword4.text.toString() != "") v.setBackgroundResource(R.drawable.underline_yellow)
            else v.setBackgroundResource(R.drawable.underline_grey)
        }
    }

}