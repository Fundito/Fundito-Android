package com.fundito.fundito.presentation.charge

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.R
import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.removeLatestMoneyCharacter
import com.fundito.fundito.common.util.toMoneyLong
import com.fundito.fundito.common.widget.KeyboardDialogFragment.Companion.PASSWORD_MAX_LEN
import com.fundito.fundito.common.widget.hideKeyboard
import com.fundito.fundito.common.widget.observeOnce
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
        observeViewModel()
    }

    private fun initView() {
        mBinding.moneyContainer setOnDebounceClickListener {
            showKeyboard(onNumberClick = { number ->

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

            })
        }

        mBinding.toolbar.backButton setOnDebounceClickListener {
            onBackPressed()
        }

        mBinding.completeButton setOnDebounceClickListener {
            showKeyboard(true, {

            }, { password ->
                /**
                 * Password Change
                 */
                if (password.length == PASSWORD_MAX_LEN)
                    mViewModel.onTypedPasswordComplete(password)
            })
        }
    }

    private fun observeViewModel() {
        mViewModel.apply {
            passwordMatch.observeOnce(this@ChargeActivity) { matched ->

                /**
                 * 패스워드 매칭 성공
                 */
                if (matched) {
                    showCompleteScreen()
                }
                /**
                 * 패스워드 매칭 실패
                 */
                else {

                }

            }
        }
    }

    private fun showCompleteScreen() {
        hideKeyboard()
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .add(mBinding.completeFragmentContainer.id, ChargeCompleteFragment.newInstance(), "CompleteScreen")
            .commit()
        mBinding.completeFragmentContainer.isVisible = true
    }

    private fun hideCompleteScreen() {
        supportFragmentManager.findFragmentByTag("CompleteScreen")?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
        mBinding.completeFragmentContainer.isVisible = false
    }

}