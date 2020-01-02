package com.fundito.fundito.presentation.login

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.children
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.showAlert
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.observeOnce
import com.fundito.fundito.databinding.ActivityLoginPasswordBinding
import com.fundito.fundito.presentation.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.dialog_keyboard.*
import javax.inject.Inject

/**
 * Created by mj on 01, January, 2020
 */
class LoginPasswordActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    companion object {
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_NICKNAME = "ARG_NICKNAME"

        fun newIntent(context: Context, name: String, nickname: String) = Intent(context, LoginPasswordActivity::class.java).apply {
            putExtra(ARG_NAME, name)
            putExtra(ARG_NICKNAME, nickname)
        }
    }

    private val userName: String
        get() = intent?.getStringExtra(ARG_NAME) ?: ""
    private val nickName: String
        get() = intent?.getStringExtra(ARG_NICKNAME) ?: ""

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

        mViewModel.name = userName
        mViewModel.nickName = nickName

        observeViewModel()
        initKeyboard()

        window.statusBarColor = resources.getColor(R.color.dark_navy)
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
        if(mViewModel.phase.value == 0) {
            mViewModel.onTypedPassword(num)
        }else {
            mViewModel.onTypedPasswordCheck(num)
        }
    }


    private fun adjustCircleImages() {

        val curLength = if(mViewModel.phase.value == 0) mViewModel.password.value?.length ?: 0 else mViewModel.passwordCheck.value?.length ?: 0

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


    private fun onPasswordMatchFailed() {
        startShakeAnim()
        mBinding.info.text = "비밀번호가 일치하지 않습니다."
    }

    private fun observeViewModel() {
        mViewModel.apply {
            password.observe(this@LoginPasswordActivity) {
                adjustCircleImages()
            }
            passwordCheck.observe(this@LoginPasswordActivity) {
                adjustCircleImages()
            }
            phase.observe(this@LoginPasswordActivity) {

                when(it) {
                    0 -> mBinding.info.text = "펀디토에서 사용하실\n비밀번호 6자리를 등록해주세요"
                    1 -> mBinding.info.text = "한번 더 입력해주세요"
                }

                adjustCircleImages()
            }

            shakeAnim.observeOnce(this@LoginPasswordActivity) {
                startShakeAnim()
            }
            passwordMatched.observeOnce(this@LoginPasswordActivity) {
                if(!it) {
                    mBinding.info.text = "비밀번호가 다름니다\n한번 더 입력해주세요"
                    onPasswordMatchFailed()
                }
            }
            signUpResult.observeOnce(this@LoginPasswordActivity) {
                if(it) {
                    startActivity(MainActivity::class, Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }else {
                    showAlert("회원가입 실패")
                }
            }

        }
    }
}