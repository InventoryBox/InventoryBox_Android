package com.inventorybox.inventorybox.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.R
import kotlinx.android.synthetic.main.activity_terms_personal.*

class TermsPersonal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_personal)

        btn_back_terms_personal.setOnClickListener { finish() }

        tv_terms_personal.text = Html.fromHtml(getString(R.string.terms_personal),Html.FROM_HTML_MODE_COMPACT)

    }
}