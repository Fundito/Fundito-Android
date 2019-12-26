package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fundito.fundito.R

/**
 * Created by mj on 26, December, 2019
 */
class FundingProgressFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_progress,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
    
}