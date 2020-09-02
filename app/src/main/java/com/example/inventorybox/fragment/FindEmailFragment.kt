package com.example.inventorybox.fragment

import android.app.DownloadManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.example.inventorybox.R
import com.example.inventorybox.data.RequestFindEmail
import com.example.inventorybox.etc.showCustomToast
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.acitivity_home_profile.*
import kotlinx.android.synthetic.main.acitivity_home_profile.btn_profile
import kotlinx.android.synthetic.main.activity_sign_up_personal.*
import kotlinx.android.synthetic.main.activity_sign_up_terms.*
import kotlinx.android.synthetic.main.fragment_find_email.*

class FindEmailFragment : Fragment() {

    val requestToServer = RequestToServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val watcher =object: TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                button()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }

        find_email_et_bussiness.addTextChangedListener(watcher)
        find_email_et_tel.addTextChangedListener(watcher)
        find_email_et_store.addTextChangedListener(watcher)


        find_email_et_bussiness.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.rec9_grey_yellow_border)
                }
                else {
                    v.setBackgroundResource(R.drawable.rec9_grey)
                }
            }

        find_email_et_store.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.rec9_grey_yellow_border)
                }
                else {
                    v.setBackgroundResource(R.drawable.rec9_grey)
                }
            }

        find_email_et_tel.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundResource(R.drawable.rec9_grey_yellow_border)
                }
                else {
                    v.setBackgroundResource(R.drawable.rec9_grey)
                }
            }

        btn_find_email_fin.setOnClickListener {
            val repName = find_email_et_bussiness.text.toString()
            val coName = find_email_et_store.text.toString()
            val phoneNumber = find_email_et_tel.text.toString()

            requestToServer.service.requestFindEamil(
                getString(R.string.test_token),
                RequestFindEmail(
                    repName = repName,
                    coName = coName,
                    phoneNumber = phoneNumber
                )
            ).customEnqueue(
                onFail = {
                    Log.d("find email", "이메일 찾기 실패")
                    context!!.showCustomToast("이메일을 찾지 못했습니다")
                },
                onSuccess = {
                    Log.d("find email", "이메일 찾기 성공")

                    tv_email.text = it.data.email
                    email_result.visibility = View.VISIBLE

                    btn_find_email_fin.visibility = View.GONE
                }
            )
        }
    }


    fun button(){
        if(find_email_et_bussiness.text.toString().isNotBlank() && find_email_et_store.text.toString().isNotBlank() && find_email_et_tel.text.toString().isNotBlank()){
            btn_find_email_fin.setBackgroundResource(R.drawable.rec30_yellow_gradient)
            btn_find_email_fin.isEnabled=true
        }else{
            btn_find_email_fin.setBackgroundResource(R.drawable.graph_rec30_middlegrey)
            btn_find_email_fin.isEnabled = false
        }
    }


}