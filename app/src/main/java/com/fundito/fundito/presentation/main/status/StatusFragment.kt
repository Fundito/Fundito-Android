package com.fundito.fundito.presentation.main.status

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
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
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.fundito.fundito.R
import com.fundito.fundito.common.fadeIn
import com.fundito.fundito.common.post
import com.fundito.fundito.common.startMoneyAnimation
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.util.toPixel
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.LockableBottomSheetBehavior
import com.fundito.fundito.common.widget.observeOnce
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.FragmentStatusBinding
import com.fundito.fundito.databinding.LayoutStatus3Scene1Binding
import com.fundito.fundito.databinding.LayoutStatus3Scene2Binding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.charge.ChargeActivity
import com.fundito.fundito.presentation.main.MainActivity
import com.fundito.fundito.presentation.store.StoreDetailActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
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
     * Sheet2 Scene Bindings
     */
    private val scene1Binding: LayoutStatus3Scene1Binding by lazy {
        LayoutStatus3Scene1Binding.inflate(LayoutInflater.from(requireContext())).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = mViewModel
        }
    }
    private val scene2Binding: LayoutStatus3Scene2Binding by lazy {
        LayoutStatus3Scene2Binding.inflate(LayoutInflater.from(requireContext())).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.vm = mViewModel
        }
    }

    /**
     * Backpress event handling
     */
    private val backPressedDispatcher by lazy { requireActivity().onBackPressedDispatcher }
    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {

            if (mViewModel.sceneIndex.value == 1) {
                mViewModel.sceneIndex.value = 0
                return
            }

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
        mBinding.bottomSheet1.lifecycleOwner = viewLifecycleOwner
        mBinding.bottomSheet2.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        adjustBottomSheetHeight()
        initBottomSheets()
        initView()
        observeViewModel()
        backPressedDispatcher.addCallback(viewLifecycleOwner,backPressedCallback)
    }

    private fun adjustBottomSheetHeight() {

        /**
         * 스크린 크기 - 하단 메뉴높이 - 툴바 높이 - 대략의 status bar 높이
         */
        val estimatedSheetHeight = resources.displayMetrics.heightPixels - resources.getDimension(R.dimen.bottomNavigiationViewHeight)  /* - resources.getDimension(R.dimen.toolbarHeight) */ - 44.toPixel()
        mBinding.bottomSheet1.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
        mBinding.bottomSheet2.root.doOnLayout {
            it.updateLayoutParams { height = estimatedSheetHeight.roundToInt() }
        }
    }

    private fun initBottomSheets() {
        sheet1Behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SetTextI18n")
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mBinding.bottomSheet1.remain.text = "${((mViewModel.funditoMoney.value?:0) * slideOffset).roundToLong().toMoney()} 원"
                mBinding.bottomSheet1.funding.text = "${((mViewModel.fundingData.value?.totalFundedMoney?:0) * slideOffset).roundToLong().toMoney()} 원"
                mBinding.bottomSheet1.maxReturnPrice.text = "${((mViewModel.fundingData.value?.totalRewardPercent?:0) * slideOffset).roundToLong().toMoney()} 원"
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
    }

    private fun initView() {

        //region base screen
        mBinding.arrow.doOnLayout {
            it.pivotY = it.height.toFloat()
        }

        //endregion

        //region Sheet1
        mBinding.bottomSheet1.scrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            mBinding.bottomSheet1.shadow.isActivated = v?.canScrollVertically(-1) ?: false
        }

        mBinding.bottomSheet1.charge setOnDebounceClickListener {
            startActivity(ChargeActivity::class)
        }

        mBinding.bottomSheet1.recyclerView.apply {
            adapter = RecentFundingAdapter()
        }
        //endregion

        //region Sheet2

        scene1Binding.onGoingRecyclerView.apply {
            adapter = FundingOnGoingAdapter {
                mViewModel.sceneIndex.value = 1
            }
            addItemDecoration(LinearItemDecoration(15))
        }

        scene1Binding.completeRecyclerView.apply {
            adapter = FundingCompleteAdapter()
            addItemDecoration(LinearItemDecoration(15))
        }

        scene1Binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                mViewModel.sheet2TabIndex.value = tab?.position
            }
        })

        scene2Binding.recyclerView.apply {
            adapter = RecentFundingAdapter()
        }

        scene2Binding.detail setOnDebounceClickListener {
            startActivity(StoreDetailActivity::class)
        }

        //endregion


        MainActivity.menu.observe(viewLifecycleOwner) {
            adjustSystemUIs()
        }



    }

    private fun startBackgroundAnimations() {
        ObjectAnimator.ofFloat(mBinding.arrow,"scaleY",0f,1f).apply {
            duration = 500L
            start()
        }

        mBinding.price.startMoneyAnimation(mViewModel.fundingData.value?.totalGetMoney ?: 0," 원")
    }

    private fun observeViewModel() {
        MainActivity.menu.observe(viewLifecycleOwner) {
            if(it == MainActivity.MainMenu.STATUS) {
                startBackgroundAnimations()
            }
            adjustSystemUIs()
        }

        mViewModel.apply {

            dispatchBackPressEvent.observeOnce(viewLifecycleOwner) {
                backPressedDispatcher.onBackPressed()
            }

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
                        sheet1Behavior.swipeEnabled = false
                        sheet2Behavior.swipeEnabled = true

                        backPressedCallback.isEnabled = true
                        mBinding.bottomSheet2.root.fadeIn(duration = 200L)

                    }

                    2-> {
                        sheet1Behavior.swipeEnabled = false
                        sheet2Behavior.swipeEnabled = false
                    }
                }
            }

            sheet2TabIndex.observe(viewLifecycleOwner) {index->
                if (scene1Binding.tabLayout?.selectedTabPosition != index)
                    scene1Binding.tabLayout.getTabAt(index)?.select()
            }

            sceneIndex.observe(viewLifecycleOwner) { index ->
                val scene = Scene(mBinding.bottomSheet2.sceneContainer, if (index == 0) scene1Binding.root else scene2Binding.root)
                TransitionManager.go(scene)

                adjustSystemUIs()

            }

            userData.observe(viewLifecycleOwner) {
                mBinding.info.text = "${it.name}님이 현재 얻을 수 있는 금액은?"
            }
            fundingData.observe(viewLifecycleOwner) {
                mBinding.info2.text = buildSpannedString {
                    append("원금대비 ")
                    bold {
                        color(resources.getColor(R.color.blueberry)) {
                            append("${it.totalRewardPercent}%")
                        }
                    }
                    color(resources.getColor(R.color.coral)){
                        append(" 상승!")
                    }
                }

                startBackgroundAnimations()

                mBinding.bottomSheet1.funding.text = it.totalFundedMoney.toMoney() + " 원"
                mBinding.bottomSheet1.maxReturnPrice.text = it.totalRewardMoney.toMoney()+ " 원"
            }
            funditoMoney.observe(viewLifecycleOwner) {
                mBinding.bottomSheet1.remain.text = it.toMoney() + " 원"
            }
        }
    }

    private fun adjustSystemUIs() {

        if(mViewModel.sceneIndex.value == 1 && MainActivity.menu.value == MainActivity.MainMenu.STATUS) {
            requireActivity().window.statusBarColor = resources.getColor(R.color.blueberry_two)
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }else if(MainActivity.menu.value == MainActivity.MainMenu.STATUS){
            requireActivity().window.statusBarColor = Color.WHITE
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}