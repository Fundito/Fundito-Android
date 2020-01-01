package com.fundito.fundito.presentation.funding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FundingPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FundingInputFragment()
            1 -> FundingProgressFragment()
            2 -> FundingCompleteFragment()
            else -> throw IllegalArgumentException()
        }
    }

}