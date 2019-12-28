package com.fundito.fundito.presentation.main.status

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.fadeIn
import com.fundito.fundito.common.post
import com.fundito.fundito.common.startMoneyAnimation
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.util.toPixel
import com.fundito.fundito.common.widget.LockableBottomSheetBehavior
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentStatusBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.charge.ChargeActivity
import com.fundito.fundito.presentation.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.math.roundToLong

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
    private val sheet1Behavior : LockableBottomSheetBehavior<*> by lazy { (mBinding.bottomSheet1.root.layoutParams as CoordinatorLayout.LayoutParams).behavior as LockableBottomSheetBehavior }
    private val sheet2Behavior : LockableBottomSheetBehavior<*> by lazy { (mBinding.bottomSheet2.root.layoutParams as CoordinatorLayout.LayoutParams).behavior as LockableBottomSheetBehavior }

    /**
     * Backpress event handling
     */
    private val backPressedDispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val curSheetCount = mViewModel.sheetOpenCount.value ?: 0

            when(curSheetCount) {
                1 -> sheet1Behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                2 -> sheet2Behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }

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
        Timber.e(estimatedSheetHeight.toString())
        mBinding.bottomSheet1.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
        mBinding.bottomSheet2.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
    }

    private fun initView() {

        mBinding.bottomSheet1.scrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            mBinding.bottomSheet1.shadow.isActivated = v?.canScrollVertically(-1) ?: false
        }

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
            @SuppressLint("SetTextI18n")
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mBinding.bottomSheet1.remain.text = "${(120_000 * slideOffset).roundToLong().toMoney()} 원"
                mBinding.bottomSheet1.funding.text = "${(30000 * slideOffset).roundToLong().toMoney()} 원"
                mBinding.bottomSheet1.maxReturnPrice.text = "${(58500 * slideOffset).roundToLong().toMoney()} 원"
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when(newState) {
                    BottomSheetBehavior.STATE_EXPANDED-> {
                        mViewModel.sheetOpenCount.value = 1
                    }
                    BottomSheetBehavior.STATE_COLLAPSED-> {
                        mViewModel.sheetOpenCount.value = 0
                    }
                    else->{}
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
                    else->{}
                }
            }
        })

        mBinding.bottomSheet1.recyclerView.apply {
            adapter = RecentFundingAdapter().apply { submitList(listOf("","","","")) }

        }
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
                        sheet1Behavior.swipeEnabled = true
                        sheet2Behavior.swipeEnabled = false

                        backPressedCallback.isEnabled = false

                        mBinding.bottomSheet1.root.fadeIn(duration = 200L)
                        mBinding.bottomSheet2.root.isVisible = false
                    }

                    1 -> {
                        sheet1Behavior.swipeEnabled = true
                        sheet2Behavior.swipeEnabled = true

                        backPressedCallback.isEnabled = true
                        mBinding.bottomSheet2.root.fadeIn(duration = 200L)

                    }

                    2-> {
                        sheet1Behavior.swipeEnabled = false
                        sheet2Behavior.swipeEnabled = true
                    }
                }
            }
        }
    }


}