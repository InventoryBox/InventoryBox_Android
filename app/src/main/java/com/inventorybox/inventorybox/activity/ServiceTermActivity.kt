package com.inventorybox.inventorybox.activity

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.inventorybox.inventorybox.R
import kotlinx.android.synthetic.main.activity_service_term.*

class ServiceTermActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_term)

        btn_back_service_term.setOnClickListener {
            finish()
        }

        tv_service_terms_condition1.setText(Html.fromHtml(getString(R.string.terms1), Html.FROM_HTML_MODE_COMPACT))
        tv_service_terms_condition2.setText(Html.fromHtml(getString(R.string.terms2), Html.FROM_HTML_MODE_COMPACT))
        tv_service_terms_condition3.setText(Html.fromHtml(getString(R.string.terms3), Html.FROM_HTML_MODE_COMPACT))

    }


}