package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.R
import dagger.android.support.DaggerFragment

/**
 * Created by mj on 26, December, 2019
 */
class FundingProgressFragment : DaggerFragment() {

    private val mViewModel: FundingViewModel by lazy{ ViewModelProvider(requireActivity())[FundingViewModel::class.java]}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun observeViewModel() {

    }
}