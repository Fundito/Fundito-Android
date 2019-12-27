package com.fundito.fundito.presentation.charge

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.animation.doOnStart
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.removeLatestMoneyCharacter
import com.fundito.fundito.common.util.toMoneyLong
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showKeyboard
import com.fundito.fundito.databinding.ActivityChargeBinding
import com.fundito.fundito.di.module.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class ChargeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var mBinding : ActivityChargeBinding
    private lateinit var mViewModel:ChargeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityChargeBinding.inflate(LayoutInflater.from(this))

        setContentView(mBinding.root)

        mViewModel = ViewModelProvider(this,viewModelFactory)[ChargeViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
    }

    private fun initView() {
        mBinding.moneyContainer setOnDebounceClickListener {
            showKeyboard {number->

                val nextText = if (number != -1) {
                    mBinding.moneyText.text.toString().addCharForMoneyRepresentation(number)
                } else {
                    mBinding.moneyText.text.toString().removeLatestMoneyCharacter()
                }

                ObjectAnimator.ofFloat(mBinding.moneyText,"translationY",0f,30f).apply {
                    duration = 50L
                    repeatCount = 1
                    repeatMode = ObjectAnimator.REVERSE

                    doOnStart { mBinding.moneyText.text = nextText }
                    start()
                }

                ObjectAnimator.ofFloat(mBinding.moneyText,"alpha",1f,0.5f).apply {
                    duration = 50L
                    repeatCount = 1
                    repeatMode = ObjectAnimator.REVERSE

                    start()
                }

                mViewModel.chargeMoney.value = nextText.toMoneyLong()

            }
        }
    }

}