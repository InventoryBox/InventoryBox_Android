package com.inventorybox.inventorybox.etc

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.io.IOException
import java.text.DecimalFormat

class PriceTextWatcher(et : EditText): TextWatcher {

    val df : DecimalFormat = DecimalFormat("#,###.##")
    val dfnd : DecimalFormat = DecimalFormat("#,###")
//    var hasFractionalPart : Boolean = false
    val et : EditText = et

    override fun afterTextChanged(s: Editable?) {
        et.removeTextChangedListener(this)
//        if(s.toString().isNotEmpty()){
            try{
                val inilen: Int= et.text.length
                if(inilen>1){
                    val v = s.toString()
                        .replace(df.decimalFormatSymbols.groupingSeparator.toString(), "")

                    val n = df.parse(v)
                    val cp = et.selectionStart
//            if (hasFractionalPart) {
//                et.setText(df.format(n))
//            } else {
//            }
                    et.setText(dfnd.format(n))
                    val endlen = et.text.length
                    val sel = cp + (endlen - inilen)
                    if (sel > 0 && sel <= et.text.length) {
                        et.setSelection(sel)
                    } else {
                        // place cursor at the end?
                        et.setSelection(et.text.length - 1)
                    }

                }


            }catch (e : IOException){

            }
            et.addTextChangedListener(this)



//        }


    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}