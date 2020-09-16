package com.example.inventorybox.activity

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.Html.TagHandler
import android.text.Spannable
import android.text.TextPaint
import android.text.style.StrikethroughSpan
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.R
import kotlinx.android.synthetic.main.activity_terms_conditions.*
import org.xml.sax.XMLReader


class TermsConditionsActivity : AppCompatActivity() {

//    lateinit var builder : SpannableStringBuilder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_conditions)


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
