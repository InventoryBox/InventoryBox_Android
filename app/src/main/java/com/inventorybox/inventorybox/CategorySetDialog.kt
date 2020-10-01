package com.inventorybox.inventorybox

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.inventorybox.inventorybox.DB.SharedPreferenceController
import com.inventorybox.inventorybox.activity.RecordAddActivity
import com.inventorybox.inventorybox.adapter.CategorySetDialogAdapter
import com.inventorybox.inventorybox.data.CategorySetInfo
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue

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
        requestData(context!!)


        dialog.show()


        return dialog
    }

    // dialog 를 밑에 꽉차게 뜨도록
    override fun onResume() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.FILL_HORIZONTAL)
        dialog?.window?.
        setGravity(Gravity.BOTTOM)
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        super.onResume()
//        requestData()
    }

    fun requestData(context: Context){
//        dialog.invalidateOptionsMenu()
//        datas = mutableListOf()
        RequestToServer.service.requestCategorySetInfo(
            SharedPreferenceController.getUserToken(context!!)
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