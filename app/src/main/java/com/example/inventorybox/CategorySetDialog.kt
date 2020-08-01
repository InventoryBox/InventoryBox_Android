package com.example.inventorybox

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybox.activity.RecordAddActivity
import com.example.inventorybox.adapter.CategorySetDialogAdapter
import com.example.inventorybox.data.CategorySetInfo
import com.example.inventorybox.network.RequestToServer
import com.example.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_category_set_dialog.*

class CategorySetDialog : DialogFragment() {

    lateinit var confirm_listener : RecordAddActivity.CategorySetListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.fragment_category_set_dialog, null)
        builder.setView(view)
        val dialog = builder.create()
        val rv_category_set = view.findViewById<RecyclerView>(R.id.rv_category_set)



//        var category_name = ""
//        var category_idx = -1

        var datas = mutableListOf<CategorySetInfo>()
        datas = requestData()

        var listener = object :CategoryClickListener{
//            fun Clicked(item: CategorySetInfo) {
//
//            }

            override fun onClick(item: CategorySetInfo) {
                confirm_listener.onSet(item)
                dialog.dismiss()
            }
        }

        val adapter = CategorySetDialogAdapter(view!!.context)
        adapter.datas = datas
        adapter.listener = listener
        rv_category_set.adapter = adapter
        adapter.notifyDataSetChanged()

        dialog.show()


        return dialog
    }

    // dialog 를 밑에 꽉차게 뜨도록
    override fun onResume() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog?.window?.setGravity(Gravity.FILL_HORIZONTAL)
        dialog?.window?.
        setGravity(Gravity.BOTTOM)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onResume()

    }

    fun requestData() : MutableList<CategorySetInfo>{
        var datas = mutableListOf<CategorySetInfo>()
        RequestToServer.service.requestCategorySetInfo(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.categoryInfo){
                    datas.add(data)
                }
            }
        )
        return datas
    }

    interface CategoryClickListener{
        fun onClick(item : CategorySetInfo)
    }
}