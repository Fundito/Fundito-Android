package com.fundito.fundito.presentation.charge

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.broadcast.Broadcast
import com.fundito.fundito.common.util.addCharForMoneyRepresentation
import com.fundito.fundito.common.util.removeLatestMoneyCharacter
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.util.toMoneyLong
import com.fundito.fundito.common.widget.*
import com.fundito.fundito.common.widget.KeyboardDialogFragment.Companion.PASSWORD_MAX_LEN
import com.fundito.fundito.databinding.ActivityChargeBinding
import com.fundito.fundito.di.module.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class ChargeActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private lateinit var mBinding : ActivityChargeBinding
    private lateinit var mViewModel:ChargeViewModel

    private var keyboardDialog : KeyboardDialogFragment? = null

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
            keyboardDialog = showKeyboard(true, {

            }, { password ->
                /**
                 * Password Change
                 */
                if (password.length == PASSWORD_MAX_LEN)
                    mViewModel.onTypedPasswordComplete(password)
            })
        }
    }

    private var isLoading = false
    private fun observeViewModel() {
        mViewModel.apply {

            loading.observe(this@ChargeActivity) {
                if(isLoading == it) return@observe
                isLoading = it
                if(it) showLoading() else hideLoading()
            }

            passwordMatch.observeOnce(this@ChargeActivity) { matched ->

                /**
                 * 패스워드 매칭 성공
                 */
                if (matched) {
                    showCompleteScreen()
                    lifecycleScope.launch {

                        val totalMoney = (mViewModel.funditoMoney.value ?: 0) + (mViewModel.chargeMoney.value?.toInt() ?: 0)

                        Broadcast.chargeCompleteEvent.send(totalMoney)
                    }

                }
                /**
                 * 패스워드 매칭 실패
                 */
                else {
                    keyboardDialog?.onPasswordMatchFailed()
                }

            }


            funditoMoney.observe(this@ChargeActivity) {
                Timber.e(it.toString())
                Timber.e(it.toMoney())
                mBinding.beforeAmmount.text = "충전 전 펀디토 머니: ${it.toMoney()}원"
                Timber.e(mBinding.beforeAmmount.text.toString())
            }
        }
    }

    private fun showCompleteScreen() {
        hideKeyboard()
        keyboardDialog = null
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
            .add(
                mBinding.completeFragmentContainer.id, ChargeCompleteFragment.newInstance(
                    mViewModel.cardData.value?.userName ?: "",
                    mViewModel.cardData.value?.cardNickname ?: "",
                    mViewModel.chargeMoney.value?.toInt() ?: 0,
                    (mViewModel.funditoMoney.value ?: 0) + (mViewModel.chargeMoney.value?.toInt() ?: 0)
                ), "CompleteScreen"
            )
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