package com.example.inventorybox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.inventorybox.activity.LoginActivity
import com.example.inventorybox.network.POST.RequestEmail
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //포커스 되면 밑줄 색상 변화
        isDataValid()

        val signup_email: String = editTextTextEmailAddress2.text.toString()
        val signup_number: String = editTextNumber2.text.toString()
        val signup_password: String = editTextTextPassword3.text.toString()
        val signup_password2: String = editTextTextPassword4.text.toString()


        //회원가입 완료 버튼 이벤트
            signup_btn_next.setOnClickListener {
                if(editTextTextEmailAddress2.text.isNullOrBlank() || editTextNumber2.text.isNullOrBlank() || editTextTextPassword3.text.isNullOrBlank() || editTextTextPassword4.text.isNullOrBlank()){
                    ifUserInfoValid(signup_email, signup_number, signup_password, signup_password2)
                } else{
                    signup_btn_next.setBackgroundResource(R.drawable.rec30_yellow)
                    startActivity(Intent(this@SignUp, LoginActivity::class.java))
                    finish()
                }

            }


        signup_btn_1.setOnClickListener {
            val signup_email: String = editTextTextEmailAddress2.text.toString()
            EmailResponse(signup_email)
        }


    }

    //비어있으면 빨간 밑줄
    private fun ifUserInfoValid(u_email: String, u_number: String, u_password: String, u_password2: String): Boolean{
        if (u_email == "" || u_number == "" || u_password == "" || u_password2 == ""){
            editTextTextEmailAddress2.setBackgroundResource(R.drawable.underline_red)
            editTextNumber2.setBackgroundResource(R.drawable.underline_red)
            editTextTextPassword3.setBackgroundResource(R.drawable.underline_red)
            editTextTextPassword4.setBackgroundResource(R.drawable.underline_red)
        }
        else
            return true
        return false
    }


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

    private fun EmailResponse(signup_email: String) {

        requestToServer.service.requestEmail(
            getString(R.string.test_token),
            RequestEmail(
                sendEmail = signup_email
            )
        ).customEnqueue(
            onFail = {
                Log.e("email failed", "fail")
                Toast.makeText(this@SignUp, "인증번호를 확인하세요!", Toast.LENGTH_SHORT).show()
                editTextTextEmailAddress2.setBackgroundResource(R.drawable.underline_red)
            },
            onSuccess = {
                Toast.makeText(this@SignUp, "이메일 성공", Toast.LENGTH_SHORT).show()
                Log.d("email", "이메일 성공")
                tv_email_msg.visibility = View.VISIBLE

                signup_btn_ok.setOnClickListener {
                    tv_number_msg.visibility = View.VISIBLE
                }
            }
        )
    }

}