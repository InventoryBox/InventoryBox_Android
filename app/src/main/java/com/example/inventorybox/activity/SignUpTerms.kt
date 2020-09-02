package com.example.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import com.example.inventorybox.SignUpPersonal
import kotlinx.android.synthetic.main.activity_sign_up_terms.*

class SignUpTerms : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_terms)

        //전체 체크박스 선택 시 모든 체크박스 선택
        signup_cb_all.setOnClickListener {
            if(signup_cb_all.isChecked){
                signup_cb_1.isChecked = true
                signup_cb_2.isChecked = true
                signup_cb_3.isChecked = true
                signup_cb_4.isChecked = true
                signup_cb_5.isChecked = true
            }else{
                signup_cb_1.isChecked = false
                signup_cb_2.isChecked = false
                signup_cb_3.isChecked = false
                signup_cb_4.isChecked = false
                signup_cb_5.isChecked = false
            }
            btnSignupTermsActivation()
        }

        signup_cb_1.setOnClickListener {
            signUpTermsAllCheckBox()
            btnSignupTermsActivation()
        }

        signup_cb_2.setOnClickListener {
            signUpTermsAllCheckBox()
            btnSignupTermsActivation()
        }

        signup_cb_3.setOnClickListener {
            signUpTermsAllCheckBox()
            btnSignupTermsActivation()
        }

        signup_cb_4.setOnClickListener {
            signUpTermsAllCheckBox()
            btnSignupTermsActivation()
        }

        signup_cb_5.setOnClickListener {
            signUpTermsAllCheckBox()
            btnSignupTermsActivation()
        }

        //뒤로가기 버튼 클릭 시 프로필설정 화면으로 이동
        btn_back_signup_terms.setOnClickListener{
            finish()
        }

        //다음 버튼 클릭 시 로그인 화면으로 이동
        btn_signup_terms.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //젠체 체크박스 상태
    fun signUpTermsAllCheckBox(){
        if(signup_cb_1.isChecked&&signup_cb_2.isChecked&&signup_cb_3.isChecked&&signup_cb_4.isChecked&&signup_cb_5.isChecked){
            signup_cb_all.isChecked = true
        }else{
            signup_cb_all.isChecked = false
        }
    }

    //필수 체크박스 선택 시 버튼 활성화
    fun btnSignupTermsActivation(){
        if(signup_cb_1.isChecked&&signup_cb_2.isChecked&&signup_cb_3.isChecked&&signup_cb_4.isChecked){
            btn_signup_terms.background = getDrawable(R.drawable.rec30_yellow_gradient)
            btn_signup_terms.isEnabled=true
        }else{
            btn_signup_terms.background = getDrawable(R.drawable.graph_rec30_middlegrey)
            btn_signup_terms.isEnabled=false
        }
    }
}