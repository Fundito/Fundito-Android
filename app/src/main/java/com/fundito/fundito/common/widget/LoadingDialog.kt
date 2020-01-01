package com.fundito.fundito.common.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.fundito.fundito.R
import com.fundito.fundito.databinding.DialogLoadingBinding

/**
 * Created by mj on 24, December, 2019
 */
fun Fragment.showLoading() {
    val dialog = LoadingDialog()
    dialog.show(childFragmentManager, "Loading")
}

fun AppCompatActivity.showLoading() {
    val dialog = LoadingDialog()
    dialog.show(supportFragmentManager, "Loading")
}

fun Fragment.hideLoading() {
    childFragmentManager.findFragmentByTag("Loading")?.let {
        childFragmentManager.beginTransaction().remove(it).commit()
    }
}

fun AppCompatActivity.hideLoading() {
    supportFragmentManager.findFragmentByTag("Loading")?.let {
        supportFragmentManager.beginTransaction().remove(it).commit()
    }
}

class LoadingDialog : DialogFragment() {

    private lateinit var mBinding: DialogLoadingBinding
    private lateinit var animator : ValueAnimator

    /**
     * 현재 BottomSheet(Fragment)의 Theme를 얻어오는 메서드를 오버라이딩 해서 우리가 커스텀하게 정의한
     *
     * RoundBottomSheetDialog 라는 Theme.Design.Light.BottomSheetDialog 스타일을 상속한 스타일을 반환하게 해준다.
     */
    override fun getTheme(): Int = R.style.AppTheme

    /**
     * 커스텀한 Dialog 객체를 반환해주기 위해 BottomSheetDialog 대화상자를 반환해준다.
     *
     * 여기서 중요한 것은 우리가 [getTheme] 메서드를 오버라이딩 해서 반환해주는 Theme 를 이용해서 [BottomSheetDialog]를 생성해주어서
     *
     * 우리가 원하는 Style 대로 BottomSheet 를 동작시킬 수 있다는 것이다.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = Dialog(requireActivity(), theme)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogLoadingBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }

        animator = ValueAnimator.ofFloat(1f,1.3f).apply {
            duration = 500L
            addUpdateListener {
                val value = it.animatedValue as Float
                mBinding.logo.scaleX = value
                mBinding.logo.scaleY = value
            }
            repeatCount = INFINITE
            repeatMode = REVERSE
            start()
        }
    }

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = context!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        dialog?.window?.setLayout(MATCH_PARENT, MATCH_PARENT)
    }
}