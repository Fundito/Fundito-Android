package com.fundito.fundito.presentation.charge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.common.widget.AutoClearedValue
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentChargeCompleteBinding
import dagger.android.support.DaggerFragment
import timber.log.Timber

/**
 * Created by mj on 28, December, 2019
 */
class ChargeCompleteFragment : DaggerFragment() {

    companion object {
        fun newInstance() : ChargeCompleteFragment {
            return ChargeCompleteFragment()
        }
    }

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
    }

    /**
     * Observe LiveDatas of ViewModel
     */
    private fun observeViewModel() {
        mViewModel.apply {

        }
    }

}