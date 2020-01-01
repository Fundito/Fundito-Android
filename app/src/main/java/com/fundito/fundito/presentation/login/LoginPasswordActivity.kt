package com.fundito.fundito.presentation.login

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.children
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.common.widget.KeyboardDialogFragment
import com.fundito.fundito.databinding.ActivityLoginPasswordBinding
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.dialog_keyboard.*
import javax.inject.Inject

/**
 * Created by mj on 01, January, 2020
 */
class LoginPasswordActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    companion object {
        private val TAG = LoginPasswordActivity::class.java.simpleName
    }

    private lateinit var mBinding: ActivityLoginPasswordBinding

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel : LoginPasswordViewModel by lazy {
        ViewModelProvider(this)[LoginPasswordViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginPasswordBinding.inflate(LayoutInflater.from(this))
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel
        setContentView(mBinding.root)

        initView()
        observeViewModel()
        initKeyboard()
    }

    private fun initView() {

    }

    private fun initKeyboard() {
        button0.setOnClickListener {
            appendPassword(0)
        }
        button1.setOnClickListener {
            appendPassword(1)
        }
        button2.setOnClickListener {
            appendPassword(2)
        }
        button3.setOnClickListener {
            appendPassword(3)
        }
        button4.setOnClickListener {
            appendPassword(4)
        }
        button5.setOnClickListener {
            appendPassword(5)
        }
        button6.setOnClickListener {
            appendPassword(6)
        }
        button7.setOnClickListener {
            appendPassword(7)
        }
        button8.setOnClickListener {
            appendPassword(8)
        }
        button9.setOnClickListener {
            appendPassword(9)
        }
        buttonBack.setOnClickListener {
            appendPassword(-1)
        }
    }

    private fun appendPassword(num: Int) {
        var password = mViewModel.password.value ?: return
        val curLength = password.length

        if ((password.length >= KeyboardDialogFragment.PASSWORD_MAX_LEN && num != -1) || (curLength == 0 && num == -1)) {
            startShakeAnim()
            return
        }

        if (num == -1) {
            password = password.dropLast(1)
        } else {
            password += "$num"
        }

        mViewModel.password.value = password


    }

    private fun adjustCircleImages(password: String) {
        val curLength = password.length

        circleContainer.children.forEachIndexed { index, view ->
            view.isActivated = curLength > index
        }
    }

    private fun startShakeAnim() {
        ObjectAnimator.ofFloat(circleContainer, "translationX", 0f, 20f).apply {
            duration = 40L
            repeatCount = 3
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
    }


    fun onPasswordMatchFailed() {
        startShakeAnim()
        mBinding.info.text = "비밀번호가 일치하지 않습니다."
    }

    private fun observeViewModel() {
        mViewModel.apply {
            password.observe(this@LoginPasswordActivity) {
                adjustCircleImages(it)
            }
        }
    }
}