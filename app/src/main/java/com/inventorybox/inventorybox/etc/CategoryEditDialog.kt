package com.inventorybox.inventorybox.etc

import android.app.AlertDialog
import android.app.Dialog
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
import com.inventorybox.inventorybox.R
import com.inventorybox.inventorybox.activity.RecordAddActivity
import com.inventorybox.inventorybox.data.CategorySetInfo
import com.inventorybox.inventorybox.network.RequestToServer
import com.inventorybox.inventorybox.network.customEnqueue
import kotlinx.android.synthetic.main.fragment_category_set_dialog.view.*

class CategoryEditDialog: DialogFragment() {

    lateinit var confirm_listener : RecordAddActivity.CategorySetListener
    var datas = mutableListOf<CategorySetInfo>()
    lateinit var adapter : CategoryEditDialogAdapter
    lateinit var dialog : AlertDialog
    lateinit var rv_category : RecyclerView
    lateinit var m_view : View
    var title = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val builder : AlertDialog.Builder = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
        val inflater : LayoutInflater = activity!!.layoutInflater
        m_view = inflater.inflate(R.layout.fragment_category_set_dialog, null)
        m_view.dialog_cate_title.text = title
        builder.setView(m_view)
        dialog = builder.create()
        rv_category = m_view.findViewById<RecyclerView>(R.id.rv_category_set)

        adapter = CategoryEditDialogAdapter(m_view.context)

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

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.show()


        return dialog
    }

    fun set(){
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    fun requestData(){
//        dialog.invalidateOptionsMenu()
//        datas = mutableListOf()
        datas = mutableListOf()
        RequestToServer.service.requestCategorySetInfo(
            SharedPreferenceController.getUserToken(context!!)
        ).customEnqueue(
            onSuccess = {
                for(data in it.data.categoryInfo){
                    datas.add(data)
//                    Log.d("####error",data.name)
                }
                adapter.datas = datas
                rv_category = m_view!!.findViewById(R.id.rv_category_set)
                rv_category.adapter = adapter
                adapter.notifyDataSetChanged()

                m_view.invalidate()
//        view?.invalidate()
                dialog.show()
            }
        )


    }

    interface CategoryClickListener{
        fun onClick(item : CategorySetInfo)
    }
}