package com.fundito.fundito.presentation.main.status

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnLayout
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.databinding.FragmentStatusBackgroundBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.main.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class StatusBackgroundFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    companion object {
        fun newInstance() = StatusBackgroundFragment()
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private lateinit var mBinding : FragmentStatusBackgroundBinding
    private lateinit var mViewModel : StatusViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentStatusBackgroundBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this)[StatusViewModel::class.java]
        mBinding.lifecycleOwner= viewLifecycleOwner
        mBinding.vm = mViewModel

        initView()
        observeViewModel()
    }

    private fun initView() {
        mBinding.arrow.doOnLayout {
            it.pivotY = it.height.toFloat()
        }
        mBinding.info2.text = buildSpannedString {
            append("원금대비 ")
            bold {
                color(resources.getColor(R.color.blueberry)) {
                    append("170%")
                }
            }
            color(resources.getColor(R.color.coral)){
                append(" 상승!")
            }
        }
    }

    private fun observeViewModel() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if(it == MainActivity.MainMenu.STATUS) {
                ObjectAnimator.ofFloat(mBinding.arrow,"scaleY",0f,1f).apply {
                    duration = 500L
                    start()
                }
            }
        }
    }
}