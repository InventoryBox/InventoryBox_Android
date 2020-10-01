package com.inventorybox.inventorybox.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.R
import kotlinx.android.synthetic.main.activity_terms_location.*

class TermsLocation :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_location)

        btn_back_terms_location.setOnClickListener { finish() }
        tv_terms_location.text = Html.fromHtml(getString(R.string.terms_location),Html.FROM_HTML_MODE_COMPACT)
    }
}