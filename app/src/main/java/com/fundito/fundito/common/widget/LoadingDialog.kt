package com.fundito.fundito.common.widget

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
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