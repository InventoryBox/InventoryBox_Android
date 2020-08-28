package com.example.inventorybox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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