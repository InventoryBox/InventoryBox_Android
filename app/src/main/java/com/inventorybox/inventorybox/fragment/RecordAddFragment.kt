package com.inventorybox.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.inventorybox.inventorybox.activity.RecordAddActivity

class RecordAddFragment : Fragment(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val intent = Intent (this.context, RecordAddActivity::class.java)
        startActivity(intent)
    }


}