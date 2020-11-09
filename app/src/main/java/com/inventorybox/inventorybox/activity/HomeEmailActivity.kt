package com.inventorybox.inventorybox.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.data.RequestSetPassword
import com.inventorybox.inventorybox.etc.showCustomToast
import com.inventorybox.inventorybox.method
import com.inventorybox.inventorybox.network.POST.RequestEmail
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_home_email.*
import kotlinx.android.synthetic.main.activity_home_email.et_email
import kotlinx.android.synthetic.main.activity_home_email.et_pw

class HomeEmailActivity : AppCompatActivity() {

    var auth_number = -1
    var isPasswordSet = false
    var isPassword2Set = false
    var isAuthorized = false
    var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_email)

        //뒤로가기 버튼
        btn_back_home_email.setOnClickListener {
            finish()
        }

        //인증하기 버튼 클릭시
        btn_email_confirm.setOnClickListener {
            // 이메일 전송
            val email = et_email.text.toString()

            if(email.contains('@')&&email.contains('.')){
                sendEmailForPassword(email)
                tv_email_error_msg.setMessage(et_email, "인증번호를 전송했습니다.")
            }else{
                tv_email_error_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
            }
        }

        //이메일 입력창에 입력 시 인증버튼 활성화
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    btn_email_confirm.isEnabled=true
                    btn_email_confirm.background.setTint(resources.getColor(R.color.darkgrey))
                }else{
                    btn_email_confirm.isEnabled=false
                    btn_email_confirm.background.setTint(resources.getColor(R.color.middlegrey))

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        //인증번호 입력창에 입력 시 확인버튼 활성화
        et_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    btn_number_confirm.isEnabled=true
                    btn_number_confirm.background.setTint(resources.getColor(R.color.darkgrey))
                }else{
                    btn_number_confirm.isEnabled=false
                    btn_number_confirm.background.setTint(resources.getColor(R.color.middlegrey))

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        //인증번호 일치 확인
        btn_number_confirm.setOnClickListener {
            if(et_number.text.toString().equals(auth_number.toString())){
                tv_number_error_msg.setMessage(et_number, "이메일 인증이 완료되었습니다.")
                isAuthorized=true
                email = et_email.text.toString()
                saveBtnActivation()
            }else{
                tv_number_error_msg.setErrorMessage(et_number, "인증번호가 일치하지 않습니다.")
                isAuthorized=false
            }
        }

        //비밀번호 *로 표시
        et_pw.transformationMethod = method()
        et_pw_check.transformationMethod = method()

        //비밀번호 입력창에 입력 시 버튼 활성화 및 비밀번호가 형식과 일치하는지 확인
        et_pw.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if(s!=null&&isValidPassword(s.toString())){
                    isPasswordSet=true
                    tv_pw_error_msg.visibility = View.INVISIBLE
                    tv_pw_error_msg.setMessage2(et_pw, "")
                    saveBtnActivation()
                }else{
                    isPasswordSet=false
                    tv_pw_error_msg.setErrorMessage2(et_pw, "8~12자 이내의 문자, 숫자, 특수문자의 조합하여 입력해주세요.")
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        // 비밀번호 입력란과 비밀번호 확인란 일치 확인
        et_pw_check.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val pw = et_pw.text.toString()
                if(s.toString() == pw&&isPasswordSet){
                    tv_pw_check_error_msg.setMessage2(et_pw_check, "")
                    isPassword2Set=true
                    saveBtnActivation()
                }else{
                    tv_pw_check_error_msg.setErrorMessage2(et_pw_check, "입력하신 비밀번호와 일치하지 않습니다.")
                    isPassword2Set=false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        //변경완료 버튼 클릭 시 새로운 패스워드 전해주기
        btn_pw_change.setOnClickListener {
            val newPassword = et_pw_check.text.toString()
            sendPassword(newPassword)
            finish()
        }

    }

    private fun sendEmailForPassword(email: String) {

        RequestToServer.service.requestEmailForPassword(
            RequestEmail(
                sendEmail = email
            )
        ).customEnqueue(
            onFail = {
                tv_email_error_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
            },
            onSuccess = {
                // 성공적으로 이메일 전송
                try{
                    auth_number = it.data.number
                }catch (e : Exception){
                    tv_email_error_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
                }
            }
        )
    }

    private fun sendPassword(pw: String) {
        RequestToServer.service.requestSetPassword(
            RequestSetPassword(
                updatedPassword = pw, email = email
            )
        ).customEnqueue(
            onFail = {
                this.showCustomToast("비밀번호를 확인하세요!")
            },
            onSuccess = {
                Log.d("set password", "비밀번호 재설정 성공")
            }
        )
    }

    //이메일 관련 et 에러 시 background
    fun TextView.setErrorMessage(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.underline_red)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.lightred))
        this.visibility = View.VISIBLE
    }

    //이메일 관련 et 정상적일 때 background
    fun TextView.setMessage(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.signup_edittext_selector)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.yellow))
        this.visibility = View.VISIBLE
    }

    //비밀번호 관련 et 에러 시 background
    fun TextView.setErrorMessage2(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.signup_profile_et_background_error)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.lightred))
        this.visibility = View.VISIBLE
    }

    //비밀번호 관련 et 정상 시 background
    fun TextView.setMessage2(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.signup_profile_et_backgrond)
        this.setText(message)
        this.setTextColor(resources.getColor(R.color.yellow))
        this.visibility = View.VISIBLE
    }

    //8~12자 이내의 문자, 숫자, 특수문자의 조합 확인
    fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,12}$"
        return password.matches(Regex(pattern))
    }

    //변경내용 저장 버튼 활성화
    fun saveBtnActivation(){
        if(isAuthorized && isPasswordSet && isPassword2Set){
            btn_pw_change.isEnabled=true
            btn_pw_change.setBackgroundResource(R.drawable.rec30_yellow_gradient)
        }
        else{
            btn_pw_change.isEnabled=false
            btn_pw_change.background.setTint(resources.getColor(R.color.middlegrey))
        }
    }

}

