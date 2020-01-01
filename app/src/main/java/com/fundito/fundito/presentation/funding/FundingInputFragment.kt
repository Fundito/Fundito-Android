package com.fundito.fundito.presentation.funding


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_funding.*
import kotlinx.android.synthetic.main.fragment_funding_input.*


/**
 * Created by mj on 26, December, 2019
 */
class FundingInputFragment : DaggerFragment() {

    private val mViewModel: FundingViewModel by lazy{ ViewModelProvider(requireActivity())[FundingViewModel::class.java]}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dialView.onInvestmentValueChangedListener = { number ->
            mViewModel.inputMoney.value = number
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.apply {

            inputMoney.observe(viewLifecycleOwner) {
                fundinginput_txt.setTextColor(Color.BLACK)
                fundinginput_txt.text = it.toMoney()
                adjustTexts()

                if(it > 0) {
                    requireActivity().completeButton.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                    requireActivity().completeButton.setTextColor(Color.WHITE)
                }else {
                    requireActivity().completeButton.setBackgroundColor(Color.WHITE)
                    requireActivity().completeButton.setTextColor(resources.getColor(R.color.dark_navy))
                }
            }

            funditoMyMoney.observe(viewLifecycleOwner) {
                funditoMoney.text = "펀디토머니:${it.toMoney()}원/부족금액은 자동충전 됨"
                adjustTexts()
            }

            cardData.observe(viewLifecycleOwner) {
                fundingCardNumber.text = it.cardNickname
            }
        }
    }

    private fun adjustTexts() {
        val myMoney = mViewModel.funditoMyMoney.value ?: return
        val input = mViewModel.inputMoney.value ?: return

        val diff = myMoney - input

        if(diff < 0) {

            fundingCardNumber.isVisible = true
            requiredCharging.isVisible = true
            requiredCharging.text = "충전: ${(-diff).toMoney()}원"
            funditoMoney.isVisible = false

        }else {

            fundingCardNumber.isVisible = false
            requiredCharging.isVisible =false
            funditoMoney.isVisible = true
        }

    }
}