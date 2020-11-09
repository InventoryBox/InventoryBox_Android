package com.inventorybox.inventorybox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_onboarding.*

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOnboarding.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOnboarding(val index : Int) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(index==0){
            frag_onboard_title.text = "재고 기록"
            frag_onboard_content.text = "매일매일 간단하게 재고를 기록해요"
            Glide.with(this).load(R.drawable.onboarding_img_0).into(frag_onboard_img)
        }else if(index==1){
            frag_onboard_title.text = "재고 관리"
            frag_onboard_content.text = "주간 기록을 통해 우리 가게만의 재고관리를 해요"
            Glide.with(this).load(R.drawable.onboarding_img_1).into(frag_onboard_img)
        }else if(index ==2){
            frag_onboard_title.text = "발주 알림"
            frag_onboard_content.text = "재료별 발주 시기를 놓칠 걱정은 없어요"
            Glide.with(this).load(R.drawable.onboarding_img_2).into(frag_onboard_img)
        }else{
            frag_onboard_title.text = "재고 교환"
            frag_onboard_content.text = "갑자기 재고가 떨어져도 주변에서 쉽게 구할 수 있어요"
            Glide.with(this).load(R.drawable.onboarding_img_3).into(frag_onboard_img)
        }
    }


}