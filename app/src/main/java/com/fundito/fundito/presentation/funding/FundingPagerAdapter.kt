package com.fundito.fundito.presentation.funding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FundingPagerAdapter(fm:FragmentManager, private val num_fragment: Int): FragmentStatePagerAdapter(fm){


    override fun getItem(p0: Int): Fragment {
        return when(p0){
            0 -> FundingInputFragment()
            1 -> FundingProgressFragment()
            2 -> FundingCompleteFragment()
            else -> throw IllegalArgumentException()
        }
    }

    override fun getCount(): Int {
        return num_fragment
    }
}