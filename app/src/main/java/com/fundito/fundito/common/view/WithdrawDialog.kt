package com.fundito.fundito.common.view

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.fundito.fundito.R
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.widget.AutoClearedValue
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.DialogWithdrawBinding
import java.io.Serializable
import kotlin.math.roundToInt

/**
 * Created by mj on 02, January, 2020
 */
class WithdrawDialog : DialogFragment() {


    companion object {

        private const val ARG_FUND_MONEY = "ARG_FUND_MONEY"
        private const val ARG_REFUND_MONEY = "ARG_REFUND_MONEY"
        private const val ARG_USER_NAME = "ARG_USER_NAME"
        private const val ARG_ON_WITHDRAW = "ARG_ON_WITHDRAW"

        fun show(fm: FragmentManager, fundMoney: Int, refundMoney: Int, name: String, onWithdraw : () -> Unit) {
            val dialog = WithdrawDialog().also {
                it.arguments = bundleOf(
                    ARG_FUND_MONEY to fundMoney,
                    ARG_REFUND_MONEY to refundMoney,
                    ARG_USER_NAME to name,
                    ARG_ON_WITHDRAW to onWithdraw as Serializable
                )
            }
            dialog.show(fm,dialog.tag)
        }

    }

    private val fundMoney: Int by lazy { arguments?.getInt(ARG_FUND_MONEY) ?: -1 }
    private val refundMoney: Int by lazy { arguments?.getInt(ARG_REFUND_MONEY) ?: -1 }
    private val name: String by lazy { arguments?.getString(ARG_USER_NAME) ?: "" }
    private val onWithdraw : () -> Unit by lazy { arguments?.getSerializable(ARG_ON_WITHDRAW) as (() -> Unit) }

    private var mBinding: DialogWithdrawBinding by AutoClearedValue()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DialogWithdrawBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.info1.text = buildSpannedString {
            append("투자금액 ${fundMoney}원   이윤 ")
            color(resources.getColor(R.color.coral)){ append(refundMoney.toString()) }
            append("원")
        }

        mBinding.info2.text = buildSpannedString {
            append("$name 님이 받을 수 있는\n")
            bold { append((fundMoney +refundMoney).toMoney()) }
            append(" 원이 있어요!")
        }

        mBinding.withdrawButton setOnDebounceClickListener {
            onWithdraw()
            dismiss()
        }
    }
    
    /**
     * For Size
     */
    override fun onResume() {
        super.onResume()
    
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        wm.defaultDisplay.getSize(point)
        val width = (point.x * 0.95f).roundToInt()
        val height = (point.y * 0.6f).roundToInt()
        dialog?.window?.setLayout(width, height)
    }
}