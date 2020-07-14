package com.example.inventorybox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.inventorybox.activity.LoginActivity
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.NetworkService
import com.example.inventorybox.network.POST.ResponseLogin
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUp : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //포커스 되면 밑줄 색상 변화
        isDataValid()

        //회원가입 완료 버튼 이벤트
        //로그인으로
        signup_btn_next.setOnClickListener {
            val signup_email: String = editTextTextEmailAddress2.text.toString()
            val signup_number: String = editTextNumber2.text.toString()
            val signup_password: String = editTextTextPassword3.text.toString()
            val signup_password2: String = editTextTextPassword4.text.toString()


            if (ifUserInfoValid(signup_email, signup_number, signup_password, signup_password2)){
                postSignupResponse(signup_email, signup_number, signup_password, signup_password2)
            }
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

    private fun postSignupResponse(u_email: String, u_number: String, u_password: String, u_password2: String){

        //데이터를 받아서 JSON 객체로 만든다
        val jsonObject = JSONObject()
        jsonObject.put("email", u_email)
        jsonObject.put("number", u_number)
        jsonObject.put("password", u_password)
        jsonObject.put("password2", u_password2)

        //gsonObject는 body로 들어간다
        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
        val postSignupResponse: Call<ResponseLogin> =
            networkService.postSignupResponse("application/json", gsonObject)
        postSignupResponse.enqueue(object : Callback<ResponseLogin>{
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("Login failed", t.toString())
            }

            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 200){
                        startActivity(Intent(this@SignUp, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        })

    }

}