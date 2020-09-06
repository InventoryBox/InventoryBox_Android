package com.example.inventorybox

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.etc.errorIncludedEnqueue
import com.example.inventorybox.network.ApplicationController
import com.example.inventorybox.network.POST.RequestEmail
import com.example.inventorybox.network.POST.ResponseEmail
import com.example.inventorybox.network.RequestToServer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONArray
import org.json.JSONObject


class SignUp : AppCompatActivity() {

    var auth_number = -1
    var isPasswordSet = false
    var isPassword2Set = false
    var isAuthNumSet = false

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
                isAuthNumSet=true
                checkBtnActivation()
            }else{
                tv_auth_num_msg.setErrorMessage(et_auth_num, "인증번호가 일치하지 않습니다.")
                isAuthNumSet=false
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
                    checkBtnActivation()
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
                val pw = et_pw.text.toString()
                if(s.toString() == pw&&isPasswordSet){
                    tv_pw2_msg.setMessage(et_pw2, "")
                    isPassword2Set=true
                    checkBtnActivation()
                }else{
                    tv_pw2_msg.setErrorMessage(et_pw2, "입력하신 비밀번호와 일치하지 않습니다.")
                    isPassword2Set=false
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })

        btn_signup_confirm.setOnClickListener {

            val global = ApplicationController
            global.email = (et_email.text.toString())
            global.password = (et_pw.text.toString())
            val intent = Intent(this, SignUpPersonal::class.java)
            startActivity(intent)
//            finish()
        }

    }

    // 모든 정보가 기입되었는지 확인하여 최종 버튼 활성화
    fun checkBtnActivation(){
        if(auth_number!=-1 && isPasswordSet && isPassword2Set && isAuthNumSet){
            btn_signup_confirm.isEnabled=true
            btn_signup_confirm.background= getDrawable(R.drawable.rec30_yellow_gradient)
        }
        else{
            btn_signup_confirm.isEnabled=false
            btn_signup_confirm.background.setTint(getColor(R.color.middlegrey))
        }
    }

    private fun sendEmail(signup_email: String) {

        RequestToServer.service.requestEmail(
            RequestEmail(
                sendEmail = signup_email
            )
        ).errorIncludedEnqueue(
            onSuccess = {
                //성공적으로 이메일 전송
                auth_number = it.data.number

            },
            onFail = {
                tv_email_msg.setErrorMessage(et_email, it.message.toString())


            },
            onError = {
                try{
                    val jObjError = JSONObject(it.errorBody()!!.string())
                    tv_email_msg.setErrorMessage(et_email, jObjError.getString("message"))
                    Log.d("########ss",jObjError.getString("message"))


                }catch (e : java.lang.Exception){
                    tv_email_msg.setErrorMessage(et_email, e.message.toString())
                    Log.d("########errorss",e.message.toString())
                }

            }
        )
//        ).customEnqueue(
//            onFail = {
//                tv_email_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
//            },
//            onSuccess = {
//                // 성공적으로 이메일 전송
//                try{
//                    auth_number = it.data.number
//                }catch (e : Exception){
//                    tv_email_msg.setErrorMessage(et_email, "올바른 이메일을 입력해주세요.")
//                }
//            }
//        )
    }

    fun isValidPassword(password: String): Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,12}$"
        return password.matches(Regex(pattern))
    }



    fun TextView.setErrorMessage(et : EditText?,  message : String){
        if(et == et_email){
            et?.setBackgroundResource(R.drawable.et_underline_red)
        }else{
            et?.setBackgroundResource(R.drawable.underline_red)
        }
        this.setText(message)
        this.setTextColor(getColor(R.color.lightred))
        this.visibility = View.VISIBLE
    }

    fun TextView.setMessage(et : EditText?, message : String){
        if(et == et_email){
            et?.setBackgroundResource(R.drawable.et_red_yellow_selector)
        }else{
            et?.setBackgroundResource(R.drawable.signup_edittext_selector)
        }
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