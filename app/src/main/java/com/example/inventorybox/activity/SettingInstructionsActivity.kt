package com.example.inventorybox.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybox.Adpater.RecordCompletedAdapter
import com.example.inventorybox.R
import com.example.inventorybox.adapter.RecordCategoryAdapter
import com.example.inventorybox.adapter.SettingInstructionsAdapter
import com.example.inventorybox.adapter.SettingInstructionsCategoryAdapter
import com.example.inventorybox.data.RecordCategorySettingData
import com.example.inventorybox.data.SettingInstructionsCategoryData
import com.example.inventorybox.data.SettingInstructionsData
import com.example.inventorybox.fragment.RecordFragment
import kotlinx.android.synthetic.main.activity_home_order_edit.*
import kotlinx.android.synthetic.main.activity_setting_instructions.*
import kotlinx.android.synthetic.main.activity_setting_instructions.view.*

class SettingInstructionsActivity : AppCompatActivity(){

    var click_index = 0

    //카테고리를 클릭할 때마다 일어나는 listener
    val category_listener = object : CategoryClickListener {
        override fun onClick(cate_index: Int) {
            //클릭이 일어난 int값(cate_index)를 click_index에 저장
            click_index = cate_index
            addContent()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_instructions)

        addCategory()
        addContent()

        //뒤로가기 이미지 클릭 시
        btn_back_setting_instructions.setOnClickListener {
            finish()
        }

        //리사이클러뷰 중복스크롤 막기
        rv_setting_instruction.setOverScrollMode(View.OVER_SCROLL_NEVER)

    }

    private fun addCategory(){
        var datas = mutableListOf<SettingInstructionsCategoryData>()
        val categoryAdapter = SettingInstructionsCategoryAdapter(this)
        categoryAdapter.listener = category_listener

        datas.add(SettingInstructionsCategoryData(0,"기본"))
        datas.add(SettingInstructionsCategoryData(1,"발주알림(홈)"))
        datas.add(SettingInstructionsCategoryData(2,"재고기록"))
        datas.add(SettingInstructionsCategoryData(3,"재고량추이"))
        datas.add(SettingInstructionsCategoryData(4,"재고교환"))
        categoryAdapter.datas = datas

        rv_instructions.adapter = categoryAdapter
        categoryAdapter.notifyDataSetChanged()
    }

    private fun addContent(){
        var contentAdapter = SettingInstructionsAdapter(this)
        var datas = mutableListOf<SettingInstructionsData>()

        contentAdapter.apply{
            if(click_index==0){
                tv_instrctions_title.text="재고창고란?"
                tv_instrctions_title_exp.text="개인 외식사업자를 위한 ‘재고관리’ 어플리케이션\n" +
                        "나만의 다이어리를 써가듯 매일매일 쉽게 기록하고 관리해보세요!"
                datas.add(SettingInstructionsData("최초 사용시, 어떻게 사용하나요?","1. 재고기록 탭에서 재료를 등록합니다.\n" +
                        "2. 당일 재고기록을 합니다.\n" +
                        "3. 홈(발주알림)에서 당일 발주가 필요한 목록을 확인할 수 있습니다. \n" +
                        "상세보기에서 체크박스에 체크를 해나가며 스스로 발주 유무를 확인할 수 있습니다.\n" +
                        "4. 재고 기록은 재고량 추이 탭에 데이터가 축적됩니다."))
            }else if(click_index==2){
                tv_instrctions_title.text="[재고기록]"
                tv_instrctions_title_exp.text="당일의 재고량을 쉽게 기록하고 데이터를 축적할 수 있습니다.\n" +
                        "재료추가를 할 때 발주 알림 기준과 발주할 수량을 설정할 수 있습니다."
                datas.add(SettingInstructionsData("재고기록은 어떻게 하나요?","재고기록 탭을 누르면 메인 페이지에 뜨는 ‘오늘 재고 등록하기’ 버튼을 통해 재고기록이 가능합니다."))
                datas.add(SettingInstructionsData("실수로 어제 재고기록을 깜빡 했어요. ㅠㅠ 어떻게 해야 하나요?","과거의 재고 기록을 하고 싶은 경우 상단 날짜를 과거로 돌려서 오른쪽 상단의 ‘재고량 수정’버튼을 이용해 수정할 수 있습니다."))
                datas.add(SettingInstructionsData("재고기록 수정은 어디서 하나요?","오른쪽 상단의 ‘재고량 수정’버튼을 통해 기록 수정이 가능합니다."))
                datas.add(SettingInstructionsData("재료 등록 방법은 무엇인가요?","왼쪽 상단의 재료추가 버튼을 이용합니다.\n" +
                        "1. 아이콘을 정해주세요.(선택사항)\n" +
                        "2. 단위를 설정해주세요.\n" +
                        "3. 카테고리를 설정해주세요.\n" +
                        "4. 발주 알림 개수를 선정해주세요. 이 개수를 기준으로 잔여재고량이 설정값 이하가 되면 홈에 발주 알림이 뜨게 됩니다.\n" +
                        "발주 알림 개수는 재고량 추이 탭에서 상세 재료를 눌러서 수정이 가능해요.\n" +
                        "5. 발주할 수량을 메모해주세요. 여러분이 주로 발주하는 량을 메모해두는 기능이에요. \n" +
                        "발주수량메모는 홈에서 ‘발주 확인 상세보기’에서도 수정이 가능해요. \n" +
                        "또, 재고량 추이 탭에서 상세 재료를 눌러서도 수정할 수 있어요."))
                datas.add(SettingInstructionsData("과거 재고기록 보기","날짜 스피너를 과거로 돌리면 과거 재고기록을 볼 수 있습니다."))
                datas.add(SettingInstructionsData("카테고리 추가, 이동, 삭제","오른쪽 상단의 폴더 버튼을 누르면 카테고리 편집이 가능합니다.\n" +
                        "이곳에서 새로운 카테고리를 추가할 수도 있고, 재료를 다른 카테고리로 이동할 수 있으며, 카테고리명을 삭제할 수 있습니다."))
                datas.add(SettingInstructionsData("재료 삭제","오른쪽 상단의 폴더 버튼을 누르면 재료삭제가 가능합니다.\n" +
                        "삭제를 원하는 재료를 클릭하면 당일 재고기록부터 삭제된 재료는 뜨지 않습니다.\n" +
                        "과거 기록에 대해서는 재료에 대한 기록이 남아있습니다."))
            }else if(click_index==1){
                tv_instrctions_title.text="[발주 알림(홈)]"
                tv_instrctions_title_exp.text="당일 발주가 필요한 재료들의 알림이 뜨고, 사용자가 체크박스를 이용하여 스스로 발주 여부를 확인할 수 있도록 돕습니다.\n" +
                        "재료별로 발주할 개수를 메모하여 일일이 기억할 필요 없이 편하게 발주를 할 수 있습니다. \n" +
                        "‘자세히’버튼을 눌렀을 때 5일간의 재고량 추이가 제공되어 사용자가 발주할 개수를 결정하는데 도움을 줍니다."
                datas.add(SettingInstructionsData("발주 알림은 무엇을 기준으로 뜨게 되나요?","재료 등록 시, 설정해 둔 발주 알림 기준 개수를 기준으로 잔여 재고량이 설정값 이하인 경우에 발주 알림이 뜨게 됩니다."))
                datas.add(SettingInstructionsData("발주 알림 기준 개수 수정은 어디서 가능한가요?","수정은 재고량 추이 탭> 해당 재료 클릭> 아래로 스와이프하여 발주 알림 기준 개수 수정이 가능합니다."))
                datas.add(SettingInstructionsData("발주 수량 메모 기능은 무엇인가요?","일반적으로 재료에 대해서 발주를 할 때 몇 개를 하는지 메모해 둘 수 있는 기능입니다.\n" +
                        "이를 통해, 일일이 모든 재료의 발주량을 외울 필요 없이 편하게 기억할 수 있습니다."))
                datas.add(SettingInstructionsData("발주 수량 메모 수정은 어디서 가능한가요?","‘발주 알림 상세보기’ 버튼 > 발주확인 페이지에서 오른쪽 상단 ‘메모수정’ 버튼을 통해서 수정이 가능합니다.\n" +
                        "또는 재고량 추이 탭> 해당 재료 선택 > 아래로 스와이프 하여 발주 수량 메모를 수정할 수 있습니다."))
                datas.add(SettingInstructionsData("발주알림 상세보기에서 ‘자세히’버튼의 기능은 무엇인가요?","최근 5일간의 재고량을 보여줍니다. 이를 참고하여 발주할 수량을 정할 수 있습니다."))
                datas.add(SettingInstructionsData("각 재료별 통계치를 더 많이 보고 싶다면 어떻게 해야 하나요?","재고량 추이 탭으로 가서 해당 재료를 클릭하면, 주간, 월간 재고량 통계치를 확인할 수 있습니다."))
                datas.add(SettingInstructionsData("프로필 및 아이디 비밀번호 변경은 어떻게 하나요?","메인 화면 오른쪽 상단의 햄버거 바를 눌러주세요. \n" +
                        "이 곳에서 프로필, 개인 정보, 이메일 및 비밀번호 변경이 가능합니다."))
            }else if(click_index==3){
                tv_instrctions_title.text="[재고량추이]"
                tv_instrctions_title_exp.text="사용자가 기록한 데이터가 주간 별로 나타나 모든 재료의 재고량 추이를 한눈에 볼 수 있습니다.\n" +
                        "발주알림개수 이하일 때 막대그래프가 노란색으로 표시되고, 이 간격을 통해 발주 주기를 파악할 수 있습니다.\n" +
                        "주간별 재고량 비교 기능을 통해 계절 또는 분기에 따른 재고량 차이를 파악할 수 있습니다.\n" +
                        "사용자는 이러한 통계치를 바탕으로 발주 알림 개수와 발주할 수량을 조절할 수 있습니다."
                datas.add(SettingInstructionsData("주간 재고량 통계를 어떻게 활용할 수 있을까요?","발주 알림 기준 개수 이하인 그래프는 노란색 그래프로 표시됩니다.\n" +
                        "노란색 그래프의 간격을 통해 발주주기를 확인할 수 있을 것입니다. 노란색 그래프의 간격(발주주기)가 너무 길다면 발주량을 줄일 필요가 있겠죠? 또 노란색 그래프의 간격(발주 주기)이 너무 짧다면 발주량을 늘릴 필요가 있을 것입니다."))
                datas.add(SettingInstructionsData("주간 별 재고량 비교 기능을 어떻게 활용할 수 있을까요?","이는 재료의 계절, 분기 별 재고량 차이 파악을 할 수 있습니다. 예를 들어, 여름의 시작이라고 할 수 있는 6월 첫째 주와, 겨울의 시작이라고 할 수 있는 11월 첫째 주의 재고량 비교를 통해 여름철과 겨울철의 발주량을 다르게 조절할 수 있을 것 입니다."))
                datas.add(SettingInstructionsData("과거의 재고량 통계를 보고 싶어요.","날짜 스피너를 과거로 돌려서 지난 재고량 통계를 볼 수 있습니다."))
            }else if(click_index==4){
                tv_instrctions_title.text="[재고교환] : 서비스 오픈 준비 중입니다."
                tv_instrctions_title_exp.text="재고교환은 외식사업자들의 식품 및 공산품 중고거래 플랫폼입니다. \n" +
                        "사용자가 가게 위치를 중심으로 반경 2km 내 가게들과 교류가 가능한 형식의 위치 기반 직거래만을 취급합니다."
                datas.add(SettingInstructionsData("최초 사용시, 어떻게 사용하나요?","1.내 가게 위치 설정을 합니다.\n" +
                        "2.내 가게를 중심으로 반경 2km 내 게시물이 뜨게 됩니다.\n" +
                        "3.제품을 사는 경우: 원하는 제품을 발견한 경우, 전화하기 버튼을 눌러 판매자와 거래할 수 있습니다.\n" +
                        "모든 거래는 직거래입니다.\n" +
                        "4.제품을 파는 경우: 오른쪽 하단의 플러스 버튼을 통해 제품 등록글을 작성할 수 있습니다.\n" +
                        "거래를 완료를 한 후에는 거래 완료 버튼을 눌러야 게시물이 사라지게 됩니다."))
                datas.add(SettingInstructionsData("제품등록 글을 수정하고 싶어요","오른쪽 상단에 책갈피 버튼을 누르면, 본인이 작성한 글의 목록을 볼 수 있습니다.\n" +
                        "여기서 게시물을 클릭하면 수정이 가능합니다."))
                datas.add(SettingInstructionsData("원하는 게시물 즐겨찾기 기능","나중에 다시 보고 싶은 게시물이 있다면 하트버튼을 통해 모아둘 수 있습니다.\n" +
                        "오른쪽 상단의 하트버튼을 누르면 즐겨찾기를 해둔 모든 게시물을 한번에 볼 수 있습니다"))
            }
        }
        contentAdapter.datas = datas
        rv_setting_instruction.adapter = contentAdapter
        contentAdapter.notifyDataSetChanged()
    }

    interface CategoryClickListener{
        fun onClick(cate_index : Int)
    }

}