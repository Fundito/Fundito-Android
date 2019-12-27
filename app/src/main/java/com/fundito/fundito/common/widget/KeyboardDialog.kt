package com.fundito.fundito.common.widget

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.fundito.fundito.R
import com.fundito.fundito.databinding.DialogKeyboardBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_keyboard.*

/**
 * Created by mj on 26, December, 2019
 */

fun AppCompatActivity.showKeyboard(onNumberClick : (Int) -> Unit) {
    val dialog = KeyboardDialogFragment()
    dialog.onNumberClick = onNumberClick
    dialog.show(supportFragmentManager,"KeyboardDialog")
}

fun AppCompatActivity.hideKeyboard() {
    (supportFragmentManager.findFragmentByTag("KeyboardDialog") as? KeyboardDialogFragment)?.let {
        it.onNumberClick = null
        supportFragmentManager.beginTransaction().remove(it).commit()
    }
}

class KeyboardDialogFragment : BottomSheetDialogFragment() {

    var onNumberClick : ((Int) -> Unit)? = null
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
        }
        button1.setOnClickListener {
            onNumberClick?.invoke(1)
        }
        button2.setOnClickListener {
            onNumberClick?.invoke(2)
        }
        button3.setOnClickListener {
            onNumberClick?.invoke(3)
        }
        button4.setOnClickListener {
            onNumberClick?.invoke(4)
        }
        button5.setOnClickListener {
            onNumberClick?.invoke(5)
        }
        button6.setOnClickListener {
            onNumberClick?.invoke(6)
        }
        button7.setOnClickListener {
            onNumberClick?.invoke(7)
        }
        button8.setOnClickListener {
            onNumberClick?.invoke(8)
        }
        button9.setOnClickListener {
            onNumberClick?.invoke(9)
        }
        buttonBack.setOnClickListener {
            onNumberClick?.invoke(-1)
        }

    }


}