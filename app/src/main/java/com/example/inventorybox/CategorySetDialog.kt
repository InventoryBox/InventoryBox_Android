package com.example.inventorybox

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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

open class CategorySetDialog : DialogFragment() {

    lateinit var confirm_listener : RecordAddActivity.CategorySetListener
    var datas = mutableListOf<CategorySetInfo>()
    lateinit var adapter : CategorySetDialogAdapter
    lateinit var dialog : AlertDialog
    lateinit var rv_category : RecyclerView
    lateinit var m_view : View
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder : AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater : LayoutInflater = activity!!.layoutInflater
        m_view = inflater.inflate(R.layout.fragment_category_set_dialog, null)
        builder.setView(m_view)
        dialog = builder.create()
        rv_category = m_view.findViewById<RecyclerView>(R.id.rv_category_set)

        adapter = CategorySetDialogAdapter(m_view.context)

        var listener = object :CategoryClickListener{
            override fun onClick(item: CategorySetInfo) {
                confirm_listener.onSet(item)
                dialog.dismiss()
            }
        }

        adapter.datas = datas
        adapter.listener = listener
        
        // data 가져와서 넣어주기
        requestData()


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
//        requestData()
    }

    fun requestData(){
//        dialog.invalidateOptionsMenu()
//        datas = mutableListOf()
        RequestToServer.service.requestCategorySetInfo(
            getString(R.string.test_token)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.categoryInfo){
                    datas.add(data)
                }
            }
        )
        adapter.datas = datas
        rv_category = m_view!!.findViewById(R.id.rv_category_set)
        rv_category.adapter = adapter
        adapter.notifyDataSetChanged()

        m_view.invalidate()
//        view?.invalidate()

    }

    interface CategoryClickListener{
        fun onClick(item : CategorySetInfo)
    }
}