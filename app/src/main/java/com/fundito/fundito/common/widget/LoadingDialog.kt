package com.fundito.fundito.common.widget

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
fun Fragment.showProgress() {
    val dialog = LoadingDialog()
    dialog.show(childFragmentManager, dialog.tag)
}
fun AppCompatActivity.showProgress(){
    val dialog = LoadingDialog()
    dialog.show(supportFragmentManager,dialog.tag)
}
class LoadingDialog : DialogFragment() {



    private lateinit var mBinding:DialogLoadingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogLoadingBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (dialog != null && dialog?.window != null) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
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
        dialog?.window?.setLayout(MATCH_PARENT , MATCH_PARENT)
    }
}