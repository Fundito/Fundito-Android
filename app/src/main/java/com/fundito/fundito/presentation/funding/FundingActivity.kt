package com.fundito.fundito.presentation.funding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import com.fundito.fundito.R
import com.fundito.fundito.common.setVisibilityBinding
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.NetworkClient
import kotlinx.android.synthetic.main.activity_funding.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.launch

/**
 * Created by mj on 26, December, 2019
 */
class FundingActivity : AppCompatActivity() {

    private var currentPage : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding)
        toolbartitle.text = "투자금액"
        backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        adaptViewPager()
        initView()
        finish()
    }

    private fun adaptViewPager(){
        fundingViewpager.adapter = FundingPagerAdapter(supportFragmentManager,3)
        fundingViewpager.offscreenPageLimit = 2

        fundingViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPage = position

                when(currentPage) {
                    0 -> {
                        completeButton.text = "입력 완료"
                    }
                    1-> {
                        completeButton.text = "투자하기"
                    }
                    2-> {
                        completeButton.text = "완료"
                    }
                }
            }
        })

    }

    private fun initView() {
        completeButton.setOnDebounceClickListener {
            when(currentPage) {
                0-> {
                    fundingViewpager.currentItem = 1
                    toolbartitle.text = "나의 투자현황"
                    progressFirstImg.setVisibilityBinding(false)
                    progressSecondImg.setVisibilityBinding(true)
                }
                1-> {
                    lifecycleScope.launch {
                        kotlin.runCatching {
                            var a = NetworkClient.storeInfoService.listStoreInfo()
                            toolbartitle.text = "목표도달까지 ${a[1].currentGoalPercent}% 남음"
                        }
                    }
                    fundingViewpager.currentItem = 2
                    progressSecondImg.setVisibilityBinding(false)
                    progressThirdImg.setVisibilityBinding(true)
                }
                2-> {

                }
            }
        }
    }

}