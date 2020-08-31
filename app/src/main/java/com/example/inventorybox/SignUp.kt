package com.example.inventorybox

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.network.POST.RequestEmail
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.security.auth.callback.PasswordCallback

class SignUp : AppCompatActivity() {

    var auth_number = -1
    var isPasswordSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_email.setOnClickListener {
            // 이메일 전송
            val email = et_email.text.toString()

            if(email.contains('@')&&email.contains('.')){
                sendEmail(email)
                tv_email_msg.setMessage(et_email, "인증번호를 전송했습니다.")
            }else{
                tv_email_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
            }
        }
        et_email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    btn_email.isEnabled=true
                    btn_email.background.setTint(getColor(R.color.darkgrey))
                }else{
                    btn_email.isEnabled=false
                    btn_email.background.setTint(getColor(R.color.middlegrey))

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        et_auth_num.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(!p0.isNullOrEmpty()){
                    btn_auth_num.isEnabled=true
                    btn_auth_num.background.setTint(getColor(R.color.darkgrey))
                }else{
                    btn_auth_num.isEnabled=false
                    btn_auth_num.background.setTint(getColor(R.color.middlegrey))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        // 인증번호 확인
        btn_auth_num.setOnClickListener {
            if(et_auth_num.text.toString().equals(auth_number.toString())){
                tv_auth_num_msg.setMessage(et_auth_num, "이메일 인증이 완료되었습니다.")
            }else{
                tv_auth_num_msg.setErrorMessage(et_auth_num, "인증번호가 일치하지 않습니다.")
            }
        }
        // * 표시
        et_pw.transformationMethod = method()
        et_pw2.transformationMethod = method()
        // 비밀번호 체크
        et_pw.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if(s!=null&&isValidPassword(s.toString())){
                    isPasswordSet=true
                    tv_pw_msg.visibility = View.INVISIBLE
                }else{
                    tv_pw_msg.setErrorMessage(null, "8~12자 이내의 문자, 숫자, 특수문자의 조합하여 입력해주세요.")
                    isPasswordSet=false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
        // 비밀번호 확인
        et_pw2.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val pw = et_pw.toString()
                if(s.toString().equals(pw)&&isPasswordSet){
                    btn_signup_confirm.isEnabled=true
                    btn_signup_confirm.background.setTint(getColor(R.color.yellow))
                }else{
                    tv_pw2_msg.setErrorMessage(et_pw2, "입력하신 비밀번호와 일치하지 않습니다.")
                    btn_signup_confirm.isEnabled=false
                    btn_signup_confirm.background.setTint(getColor(R.color.middlegrey))
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

    }


    private fun sendEmail(signup_email: String) {

        RequestToServer.service.requestEmail(
            RequestEmail(
                sendEmail = signup_email
            )
        ).customEnqueue(
            onFail = {
                tv_email_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
            },
            onSuccess = {
                // 성공적으로 이메일 전송
                try{
                    auth_number = it.data.number
                }catch (e : Exception){
                    tv_email_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
                }
            }
        )
    }

    fun isValidPassword(password: String): Boolean {
       return password.length>8
    }


    fun TextView.setErrorMessage(et : EditText?,  message : String){
        et?.setBackgroundResource(R.drawable.underline_red)
        this.setText(message)
        this.setTextColor(getColor(R.color.lightred))
        this.visibility = View.VISIBLE
    }

    fun TextView.setMessage(et : EditText?, message : String){
        et?.setBackgroundResource(R.drawable.signup_edittext_selector)
        this.setText(message)
        this.setTextColor(getColor(R.color.yellow))
        this.visibility = View.VISIBLE
    }

}
class method : PasswordTransformationMethod(){
    @Override
    override fun getTransformation(source: CharSequence?, view: View?): CharSequence {
        return PasswordCharSequence(source)
    }

    class PasswordCharSequence(val msource: CharSequence?) : CharSequence{



        override val length: Int
            get() = msource?.length ?: 0

        override fun get(index: Int): Char {
            return '*'
        }

        override fun subSequence(startIndex: Int, endIndex: Int): CharSequence {
            return msource?.subSequence(startIndex, endIndex)?:""
        }

    }
}