package com.fundito.fundito.presentation.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_funding_complete.*
import kotlin.math.roundToInt

/**
 * Created by mj on 26, December, 2019
 */
class FundingCompleteFragment : DaggerFragment() {

    private val mViewModel: FundingViewModel by lazy{ ViewModelProvider(requireActivity())[FundingViewModel::class.java]}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_funding_complete,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.apply {
            store.observe(viewLifecycleOwner) {
                storeName.text = it.name
            }

            inputMoney.observe(viewLifecycleOwner) {
                completeCost.text = it.toMoney()
                completeCost2.text = "${it.toMoney()} 원"
            }

            refundMoney.observe(viewLifecycleOwner) {
                additionalCost.text = "(+${it.roundToInt().toMoney()} 원) ${mViewModel.totalMoney.value!!.roundToInt().toMoney()} 원"
            }
        }
    }

}