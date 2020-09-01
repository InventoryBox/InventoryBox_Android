package com.example.inventorybox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.inventorybox.network.ApplicationController
import kotlinx.android.synthetic.main.activity_record_modify.*
import kotlinx.android.synthetic.main.activity_sign_up_personal.*

class SignUpPersonal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_personal)
        val watcher =object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                dataListener()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }
        signup_et_bussiness.addTextChangedListener(watcher)
        signup_et_tel.addTextChangedListener(watcher)
        signup_et_store.addTextChangedListener(watcher)

        btn_confirm_signup_personal.setOnClickListener {
            // 사업자명
            val global = ApplicationController
            global.rep_name = signup_et_bussiness.text.toString()
            global.co_name = signup_et_store.text.toString()
            global.phone_num = signup_et_tel.text.toString()



            val intent = Intent(this, SignUpProfile::class.java)
            startActivity(intent)
        }
        // 뒤로가기
        btn_back_signup_personal.setOnClickListener {
            finish()
        }
    }

    fun dataListener(){
        if(signup_et_bussiness.text.toString().isNotBlank()&&signup_et_store.text.toString().isNotBlank()&&signup_et_tel.text.toString().isNotBlank()){
            btn_confirm_signup_personal.background = getDrawable(R.drawable.rec30_yellow_gradient)
            btn_confirm_signup_personal.isEnabled=true
        }else{
            btn_confirm_signup_personal.background =getDrawable(R.drawable.graph_rec30_middlegrey)
            btn_confirm_signup_personal.isEnabled = false
        }
    }
}