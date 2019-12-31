package com.fundito.fundito.common.widget

import android.animation.ObjectAnimator
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.isVisible
import com.fundito.fundito.R
import com.fundito.fundito.databinding.DialogKeyboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_keyboard.*

/**
 * Created by mj on 26, December, 2019
 */

fun AppCompatActivity.showKeyboard(isPasswordCheck: Boolean = false, onNumberClick: (Int) -> Unit, onPasswordChanged: ((String) -> Unit)? = null) : KeyboardDialogFragment? {
    if(supportFragmentManager.findFragmentByTag("KeyboardDialog") != null) return null
    val dialog = KeyboardDialogFragment.newInstance(isPasswordCheck)
    dialog.onNumberClick = onNumberClick
    dialog.onPasswordChanged = onPasswordChanged
    dialog.show(supportFragmentManager,"KeyboardDialog")
    return dialog
}

fun AppCompatActivity.hideKeyboard() {
    (supportFragmentManager.findFragmentByTag("KeyboardDialog") as? KeyboardDialogFragment)?.let {
        it.onNumberClick = null
        supportFragmentManager.beginTransaction().remove(it).commit()
    }
}

class KeyboardDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val PASSWORD_MAX_LEN = 6

        private const val ARG_IS_PASSWORD_CHECK = "ARG_IS_PASSWORD_CHECK"

        fun newInstance(isPasswordCheck: Boolean): KeyboardDialogFragment {
            return KeyboardDialogFragment().apply {
                arguments = bundleOf(ARG_IS_PASSWORD_CHECK to isPasswordCheck)
            }
        }
    }

    private val isPasswordCheck: Boolean by lazy {
        arguments?.getBoolean(ARG_IS_PASSWORD_CHECK) ?: false
    }

    private var password = ""

    var onNumberClick : ((Int) -> Unit)? = null

    var onPasswordChanged: ((String) -> Unit)? = null

    /**
     * 현재 BottomSheet(Fragment)의 Theme를 얻어오는 메서드를 오버라이딩 해서 우리가 커스텀하게 정의한
     *
     * RoundBottomSheetDialog 라는 Theme.Design.Light.BottomSheetDialog 스타일을 상속한 스타일을 반환하게 해준다.
     */
    override fun getTheme(): Int = R.style.KeyboardBottomSheetDialog

    /**
     * 커스텀한 Dialog 객체를 반환해주기 위해 BottomSheetDialog 대화상자를 반환해준다.
     *
     * 여기서 중요한 것은 우리가 [getTheme] 메서드를 오버라이딩 해서 반환해주는 Theme 를 이용해서 [BottomSheetDialog]를 생성해주어서
     *
     * 우리가 원하는 Style 대로 BottomSheet 를 동작시킬 수 있다는 것이다.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(activity!!, theme)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
    }

    /**
     * Binding Instance
     */
    private var mBinding: DialogKeyboardBinding by AutoClearedValue()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogKeyboardBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button0.setOnClickListener {
            onNumberClick?.invoke(0)
            appendPassword(0)
        }
        button1.setOnClickListener {
            onNumberClick?.invoke(1)
            appendPassword(1)
        }
        button2.setOnClickListener {
            onNumberClick?.invoke(2)
            appendPassword(2)
        }
        button3.setOnClickListener {
            onNumberClick?.invoke(3)
            appendPassword(3)
        }
        button4.setOnClickListener {
            onNumberClick?.invoke(4)
            appendPassword(4)
        }
        button5.setOnClickListener {
            onNumberClick?.invoke(5)
            appendPassword(5)
        }
        button6.setOnClickListener {
            onNumberClick?.invoke(6)
            appendPassword(6)
        }
        button7.setOnClickListener {
            onNumberClick?.invoke(7)
            appendPassword(7)
        }
        button8.setOnClickListener {
            onNumberClick?.invoke(8)
            appendPassword(8)
        }
        button9.setOnClickListener {
            onNumberClick?.invoke(9)
            appendPassword(9)
        }
        buttonBack.setOnClickListener {
            onNumberClick?.invoke(-1)
            appendPassword(-1)
        }

        if (isPasswordCheck) {
            mBinding.passwordContainer.isVisible = true
        }

    }

    private fun appendPassword(num: Int) {
        if (!isPasswordCheck) return

        val curLength = password.length

        if ((password.length >= PASSWORD_MAX_LEN && num != -1) || (curLength == 0 && num == -1)) {
            startShakeAnim()
            return
        }

        if (num == -1) {
            password = password.dropLast(1)
        } else {
            password += "$num"
        }

        adjustCircleImages()
        startBounceAnim(password.length)

        onPasswordChanged?.invoke(password)

    }

    private fun adjustCircleImages() {
        circleContainer.children.forEachIndexed { index, view ->
            //            view.setBackgroundResource(if(password.length > index) R.drawable.white_circle else R.drawable.white_circle_stroke)
            view.isActivated = password.length > index
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

    private fun startBounceAnim(circleIdx: Int) {
        /*val circleView = circleContainer.getChildAt(circleIdx)
        ObjectAnimator.ofFloat(circleView,"translationY",0f,-150f).apply {
            duration = 100L
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            start()
        }*/
    }

    fun onPasswordMatchFailed() {
        startShakeAnim()
        mBinding.passwordInfoLabel.text = "비밀번호가 일치하지 않습니다."
    }


}