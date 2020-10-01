package com.inventorybox.inventorybox.activity

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.R
import kotlinx.android.synthetic.main.activity_terms_conditions.*


class TermsConditionsActivity : AppCompatActivity() {

//    lateinit var builder : SpannableStringBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)

    btn_back_terms_conditions.setOnClickListener { finish() }
    tv_terms_condition1.setText(Html.fromHtml(getString(R.string.terms1),Html.FROM_HTML_MODE_COMPACT))
    tv_terms_condition2.setText(Html.fromHtml(getString(R.string.terms2),Html.FROM_HTML_MODE_COMPACT))
    tv_terms_condition3.setText(Html.fromHtml(getString(R.string.terms3),Html.FROM_HTML_MODE_COMPACT))

    tv_terms_personal.setOnClickListener {
        startActivity(Intent(this,TermsPersonal::class.java))
    }
    tv_terms_location.setOnClickListener {
        startActivity(Intent(this, TermsLocation::class.java))
    }
    }
}
