package com.fundito.fundito.presentation.funding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.fundito.fundito.R
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import kotlinx.android.synthetic.main.activity_funding.*

/**
 * Created by mj on 26, December, 2019
 */
class FundingActivity : AppCompatActivity() {

    private var currentPage : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding)

        adaptViewPager()
        initView()
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
                }
                1-> {

                }
                2-> {

                }
            }
        }
    }

}