package com.fundito.fundito.presentation.charge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.widget.AutoClearedValue
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentChargeCompleteBinding
import dagger.android.support.DaggerFragment

/**
 * Created by mj on 28, December, 2019
 */
class ChargeCompleteFragment : DaggerFragment() {

    companion object {

        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_CARD_NAME = "ARG_CARD_NAME"
        private const val ARG_CHARGE_AMMOUNT = "ARG_CHARGE_AMMOUNT"
        private const val ARG_TOTAL_FUNDITO_MONEY = "ARG_TOTAL_FUNDITO_MONEY"

        fun newInstance(name: String, cardName: String, chargeAmmount: Int, totalFunditoMoney: Int) : ChargeCompleteFragment {

            return ChargeCompleteFragment().also {
                it.arguments = bundleOf(
                    ARG_NAME to name,
                    ARG_CARD_NAME to cardName,
                    ARG_CHARGE_AMMOUNT to chargeAmmount,
                    ARG_TOTAL_FUNDITO_MONEY to totalFunditoMoney
                )
            }
        }
    }

    private val name by lazy { arguments?.getString(ARG_NAME) ?: "" }
    private val cardName by lazy { arguments?.getString(ARG_CARD_NAME) ?: "" }
    private val chargeAmmount by lazy { arguments?.getInt(ARG_CHARGE_AMMOUNT) ?: 0 }
    private val totalFunditoMoney by lazy { arguments?.getInt(ARG_TOTAL_FUNDITO_MONEY) ?: 0 }

    /**
     * Binding Instance
     */
    private var mBinding: FragmentChargeCompleteBinding by AutoClearedValue()

    /**
     * ViewModel Instance
     */
    private val mViewModel: ChargeViewModel by lazy {
        ViewModelProvider(requireActivity())[ChargeViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = FragmentChargeCompleteBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        observeViewModel()
        mBinding.okButton setOnDebounceClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        mBinding.info.text = "$name 님\n${chargeAmmount.toMoney()}원 충전완료"
        mBinding.cardNumberLabel.text = cardName
        mBinding.cardOwner.text = name
        mBinding.totalMoney.text = "${totalFunditoMoney.toMoney()} 원"

        mBinding.info.isVisible = true
        mBinding.cardNumberLabel.isVisible = true
        mBinding.cardOwner.isVisible = true
        mBinding.totalMoney.isVisible = true
        mBinding.totalMoneyLabel.isVisible = true
    }

    /**
     * Observe LiveDatas of ViewModel
     */
    private fun observeViewModel() {
        mViewModel.apply {

        }
    }

}