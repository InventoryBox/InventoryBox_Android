package com.example.inventorybox.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventorybox.Adpater.RecordCompletedAdapter
import com.example.inventorybox.data.RecordCompletedData

import com.example.inventorybox.R
import com.example.inventorybox.activity.MainActivity
import com.example.inventorybox.activity.RecordAddActivity
import com.example.inventorybox.fragment.RecordAddFragment
import kotlinx.android.synthetic.main.fragment_record.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var btn_int = 0

/**
 * A simple [Fragment] subclass.
 * Use the [RecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var recordCompletedAdapter: RecordCompletedAdapter
    var datas = mutableListOf<RecordCompletedData>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordCompletedAdapter = RecordCompletedAdapter(view.context)
        rv_record_completed.adapter = recordCompletedAdapter
        loadRecordCompletedDatas()

        //재고 기록하기 버튼 클릭시 '재고기록' 액티비티 띄우기
        btn_record.setOnClickListener {
            activity?.let{
                btn_int=1
                val intent = Intent (it, RecordAddActivity::class.java)
                it.startActivity(intent)
            }
        }



    }

    private fun loadRecordCompletedDatas(){
        datas.apply {
            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    count_noti = 500,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    count_noti = 200,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    count_noti = 100,
                    count_stock = 3
                )
            )

            add(
                RecordCompletedData(
                    img = "https://cdn.pixabay.com/photo/2020/04/15/12/09/summer-5046401__480.jpg",
                    name = "우유",
                    count_noti = 500,
                    count_stock = 3
                )
            )

        }

        recordCompletedAdapter.datas = datas
        recordCompletedAdapter.notifyDataSetChanged()

    }

}