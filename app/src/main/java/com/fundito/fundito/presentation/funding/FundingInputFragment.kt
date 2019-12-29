package com.fundito.fundito.presentation.funding


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney
import kotlinx.android.synthetic.main.fragment_funding_input.*


/**
 * Created by mj on 26, December, 2019
 */
class FundingInputFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_input,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialView.onInvestmentValueChangedListener = {number->
            fundinginput_txt.setTextColor(Color.BLACK)
            fundinginput_txt.text = number.toLong().toMoney()
        }
    }

    
}