package com.fundito.fundito.presentation.funding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.fundito.fundito.R
import com.fundito.fundito.common.setVisibilityBinding
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showLoading
import com.fundito.fundito.data.service.NetworkClient
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_funding.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class FundingActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    private val mViewModel by lazy { ViewModelProvider(this,viewModelFactory)[FundingViewModel::class.java] }

    companion object {

        private const val ARG_STORE_IDX = "ARG_STORE_IDX"

        fun newIntent(context: Context, storeIdx: Int): Intent {
            return Intent(context, FundingActivity::class.java).apply {
                putExtra(ARG_STORE_IDX, storeIdx)
            }
        }
    }

    val storeId: Int
        get() = intent?.getIntExtra(ARG_STORE_IDX, -1) ?: -1

    private var currentPage : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding)
        toolbartitle.text = "투자금액"
        backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        initView()
        adaptViewPager()
        observeViewModel()
    }

    private fun adaptViewPager(){
        fundingViewpager.adapter = FundingPagerAdapter(this)
        fundingViewpager.offscreenPageLimit = 2
        fundingViewpager.isUserInputEnabled = false
        fundingViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
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

    private fun observeViewModel() {
        mViewModel.apply {
            loading.observe(this@FundingActivity) {
                if(it) showLoading() else hideLoading()
            }
        }
    }

    override fun onBackPressed() {

        when(currentPage) {
            1-> {
                fundingViewpager.setCurrentItem(0,true)
            }
            2-> {
                fundingViewpager.setCurrentItem(1,true)
            }
            else-> {
                super.onBackPressed()
            }
        }



    }
}