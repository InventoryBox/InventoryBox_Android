package com.inventorybox.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.activity.LoginActivity
import com.inventorybox.inventorybox.activity.RecordModifyActivity
import com.inventorybox.inventorybox.data.RequestSetPassword
import com.inventorybox.inventorybox.etc.showCustomToast
import com.inventorybox.inventorybox.method
import com.inventorybox.inventorybox.network.POST.RequestEmail
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_find_password.*

class FindPasswordFragment : Fragment() {

    var auth_number = -1
    var isPasswordSet = false
    var isPassword2Set = false
    var email = ""
    var isAuthorized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        et_set_pw.transformationMethod = method()
        et_set_pw_confirm.transformationMethod = method()

        find_pw_btn_email.setOnClickListener {
            // 이메일 전송
            val email = find_pw_et_email.text.toString()

            if(email.contains('@')&&email.contains('.')){
                sendEmailForPassword(email)
                find_email_tv_email_msg.setMessage(find_pw_et_email, "인증번호를 전송했습니다.")
            }else{
                find_email_tv_email_msg.setErrorMessage(find_pw_et_email, "올바른 이메일을 입력해주세요.")
            }
        }

        find_pw_et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    find_pw_btn_email.isEnabled=true
                    find_pw_btn_email.background.setTint(resources.getColor(R.color.darkgrey))
                }else{
                    find_pw_btn_email.isEnabled=false
                    find_pw_btn_email.background.setTint(resources.getColor(R.color.middlegrey))

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        find_pw_et_auth_num.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(find_pw_et_auth_num.text.toString().equals(auth_number.toString())){
                    find_pw_tv_auth_num_msg.setMessage(find_pw_et_auth_num, "인증되었습니다.")
                    email = find_pw_et_email.text.toString()
                    isAuthorized = true
                    checkBtnActivation()
                }else{
                    find_pw_tv_auth_num_msg.setErrorMessage(find_pw_et_auth_num, "인증번호가 일치하지 않습니다.")
                    isAuthorized = false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_set_pw.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if(s!=null && isValidPassword(s.toString())){
                    isPasswordSet=true
                    checkBtnActivation()
                    tv_set_pw1_msg.visibility = View.INVISIBLE
                    tv_set_pw1_msg.setMessage(et_set_pw, "")
                }else{
                    isPasswordSet=false
                    tv_set_pw1_msg.setErrorMessage(et_set_pw, "8~12자 이내의 문자, 숫자, 특수문자의 조합하여 입력해주세요.")

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        // 비밀번호 확인
        et_set_pw_confirm.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val pw = et_set_pw.text.toString()
                if(s.toString() == pw&&isPasswordSet){
                    tv_set_pw_msg.setMessage(et_set_pw_confirm, "")
                    isPassword2Set=true
                    checkBtnActivation()
                }else{
                    tv_set_pw_msg.setErrorMessage(et_set_pw_confirm, "입력하신 비밀번호와 일치하지 않습니다.")
                    isPassword2Set=false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })


        btn_find_pw_fin.setOnClickListener {
            val newPassword = et_set_pw_confirm.text.toString()
            sendPassword(newPassword)
        }

    }

    private fun sendEmailForPassword(email: String) {

        RequestToServer.service.requestEmailForPassword(
            RequestEmail(
                sendEmail = email
            )
        ).customEnqueue(
            onFail = {
                find_email_tv_email_msg.setErrorMessage(find_pw_et_email, "올바른 이메일을 입력해주세요.")
            },
            onSuccess = {
                // 성공적으로 이메일 전송
                try{
                    auth_number = it.data.number
                }catch (e : Exception){
                    find_email_tv_email_msg.setErrorMessage(find_pw_et_email, "올바른 이메일을 입력해주세요.")
                }
            }
        )
    }

    private fun sendPassword(pw: String) {

        RequestToServer.service.requestSetPassword(
            RequestSetPassword(
                    email,
                updatedPassword = pw
            )
        ).customEnqueue(
            onFail = {
                context!!.showCustomToast("비밀번호를 확인하세요!")
            },
            onSuccess = {
                Log.d("set password", "비밀번호 재설정 성공")
            }
        )

        val intent = Intent (this.context, LoginActivity::class.java)
        startActivity(intent)
    }

    fun TextView.setErrorMessage(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.underline_red)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.lightred))
        this.visibility = View.VISIBLE
    }

    fun TextView.setMessage(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.signup_edittext_selector)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.yellow))
        this.visibility = View.VISIBLE
    }

    //최종 버튼 활성화
    fun checkBtnActivation(){
        if(isAuthorized && isPasswordSet && isPassword2Set){
            btn_find_pw_fin.isEnabled=true
            btn_find_pw_fin.setBackgroundResource(R.drawable.rec30_yellow_gradient)
        }
        else{
            btn_find_pw_fin.isEnabled=false
            btn_find_pw_fin.background.setTint(resources.getColor(R.color.middlegrey))
        }
    }
    fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,12}$"
        return password.matches(Regex(pattern))
    }

}