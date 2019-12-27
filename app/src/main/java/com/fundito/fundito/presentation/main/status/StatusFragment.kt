package com.fundito.fundito.presentation.main.status

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.fadeIn
import com.fundito.fundito.common.fadeOut
import com.fundito.fundito.common.post
import com.fundito.fundito.common.startMoneyAnimation
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.util.toPixel
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentStatusBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.charge.ChargeActivity
import com.fundito.fundito.presentation.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Created by mj on 26, December, 2019
 */
class StatusFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    companion object {
        fun newInstance() = StatusFragment()
    }

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private lateinit var mBinding : FragmentStatusBinding
    private lateinit var mViewModel : StatusViewModel

    /**
     * Bottom Sheet Layouts
     */
    private val sheet1Behavior : BottomSheetBehavior<*> by lazy { BottomSheetBehavior.from(mBinding.bottomSheet1.root) }
    private val sheet2Behavior : BottomSheetBehavior<*> by lazy { BottomSheetBehavior.from(mBinding.bottomSheet2.root) }

    /**
     * Backpress event handling
     */
    private val backPressedDispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val curSheetCount = mViewModel.sheetOpenCount.value ?: 0
            if(curSheetCount > 0)
                mViewModel.sheetOpenCount post (curSheetCount - 1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = FragmentStatusBinding.inflate(inflater,container,false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewModel = ViewModelProvider(this)[StatusViewModel::class.java]
        mBinding.lifecycleOwner= viewLifecycleOwner
        mBinding.vm = mViewModel

        adjustBottomSheetHeight()
        initView()
        observeViewModel()
        backPressedDispatcher.addCallback(viewLifecycleOwner,backPressedCallback)
    }

    private fun adjustBottomSheetHeight() {

        /**
         * 스크린 크기 - 하단 메뉴높이 - 툴바 높이 - 대략의 status bar 높이
         */
        val estimatedSheetHeight = resources.displayMetrics.heightPixels - resources.getDimension(R.dimen.bottomNavigiationViewHeight) - resources.getDimension(R.dimen.toolbarHeight) - 30.toPixel()

        mBinding.bottomSheet1.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
        mBinding.bottomSheet2.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
    }

    private fun initView() {
        mBinding.arrow.doOnLayout {
            it.pivotY = it.height.toFloat()
        }
        mBinding.info2.text = buildSpannedString {
            append("원금대비 ")
            bold {
                color(resources.getColor(R.color.blueberry)) {
                    append("170%")
                }
            }
            color(resources.getColor(R.color.coral)){
                append(" 상승!")
            }
        }
        mBinding.bottomSheet1.charge setOnDebounceClickListener {
            startActivity(ChargeActivity::class)
        }
        sheet1Behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED-> {
                        mViewModel.sheetOpenCount.value = 1
                    }
                    BottomSheetBehavior.STATE_COLLAPSED-> {
                        mViewModel.sheetOpenCount.value = 0
                    }
                }
            }
        })
        sheet2Behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED-> {
                        mViewModel.sheetOpenCount.value = 2
                    }
                    BottomSheetBehavior.STATE_COLLAPSED-> {
                        mViewModel.sheetOpenCount.value = 1
                    }
                }
            }
        })
    }

    private fun startBackgroundAnimations() {
        ObjectAnimator.ofFloat(mBinding.arrow,"scaleY",0f,1f).apply {
            duration = 500L
            start()
        }

        mBinding.price.startMoneyAnimation(13_500," 원")
    }

    private fun observeViewModel() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if(it == MainActivity.MainMenu.STATUS) {
                startBackgroundAnimations()
            }
        }

        mViewModel.apply {
            sheetOpenCount.observe(viewLifecycleOwner) {count->
                when(count) {

                    0 -> {
                        backPressedCallback.isEnabled = false
                        sheet1Behavior.state = BottomSheetBehavior.STATE_COLLAPSED

                        mBinding.bottomSheet1.root.fadeIn(duration = 200L)
                        mBinding.bottomSheet2.root.fadeOut(duration = 0L)
                    }

                    1 -> {
                        sheet2Behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        backPressedCallback.isEnabled = true
                        mBinding.bottomSheet1.root.fadeIn(duration = 200L)
                        mBinding.bottomSheet2.root.fadeIn(duration = 200L)

                        mBinding.bottomSheet1.remain.startMoneyAnimation(120000," 원")
                        mBinding.bottomSheet1.funding.startMoneyAnimation(30000," 원")
                        mBinding.bottomSheet1.maxReturnPrice.startMoneyAnimation(58500," 원")
                    }
                }
            }
        }
    }


}