package com.fundito.fundito.presentation.store

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.fundito.fundito.common.widget.AutoClearedValue
import com.fundito.fundito.databinding.DialogProfitInfoBinding
import kotlin.math.roundToInt

/**
 * Created by mj on 27, December, 2019
 */
class ProfitInfoDialog : DialogFragment() {


    /**
     * Binding Instance
     */
    private var mBinding: DialogProfitInfoBinding by AutoClearedValue()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = DialogProfitInfoBinding.inflate(inflater, container, false).also { mBinding = it }.root

    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()

        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.95f).roundToInt()
        dialog?.window?.setLayout(width, WRAP_CONTENT)
    }


}