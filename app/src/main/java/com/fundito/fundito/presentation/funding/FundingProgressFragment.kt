package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_funding_progress.*
import kotlin.math.roundToInt

/**
 * Created by mj on 26, December, 2019
 */
class FundingProgressFragment : DaggerFragment() {

    private val mViewModel: FundingViewModel by lazy{ ViewModelProvider(requireActivity())[FundingViewModel::class.java]}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        refundPercent.text =  "${mViewModel.refundPercent}% 이율"
        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.apply {
            inputMoney.observe(viewLifecycleOwner) {
                fundingPriceProgress.text = it.toMoney()
            }

            funditoMyMoney.observe(viewLifecycleOwner) {
                funditoMoneyShowing.text = "펀디토 머니: ${it.toMoney()}원"
            }

            refundMoney.observe(viewLifecycleOwner) {
                val int = it.roundToInt()
                linewon.text = "${int.toMoney()} 원"
            }
            totalMoney.observe(viewLifecycleOwner) {
                val int = it.roundToInt()
                fundingTotal.text = buildSpannedString {
                    append("총 ")
                    bold { append("${int.toMoney()} 원") }
                }
            }
        }
    }

}