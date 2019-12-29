package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fundito.fundito.R
import kotlinx.android.synthetic.main.activity_funding.*
import kotlinx.android.synthetic.main.fragment_funding_input.*

/**
 * Created by mj on 26, December, 2019
 */
class FundingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funding)

    }

    private fun adaptviewpager(){
        fundingViewpager.adapter = FundingPagerAdapter(supportFragmentManager,3)
        fundingViewpager.offscreenPageLimit = 2

    }

}