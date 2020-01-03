package com.fundito.fundito.presentation.store

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.view.doOnLayout
import androidx.core.view.isVisible
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.fundito.fundito.R
import com.fundito.fundito.common.loadUrlAsync
import com.fundito.fundito.common.util.toMoney
import com.fundito.fundito.common.view.WithdrawDialog
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showLoading
import com.fundito.fundito.data.enumerator.RefundType
import com.fundito.fundito.databinding.ActivityStoreDetailBinding
import com.fundito.fundito.presentation.funding.FundingActivity
import timber.log.Timber

/**
 * Created by mj on 26, December, 2019
 */
class StoreDetailActivity : AppCompatActivity(), HasDefaultViewModelProviderFactory {

    companion object {

        private const val ARG_STORE_IDX = "ARG_STORE_IDX"

        fun newIntent(context : Context, storeIdx : Int) : Intent {
            return Intent(context, StoreDetailActivity::class.java).apply {
                putExtra(ARG_STORE_IDX, storeIdx)
            }
        }
    }

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return ViewModelFactory()
    }

    @Suppress("UNCHECKED_CAST")
    inner class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoreDetailViewModel(storeIdx) as T
        }
    }

    private lateinit var mBinding: ActivityStoreDetailBinding
    private lateinit var mViewModel: StoreDetailViewModel

    private var storeIdx = -1


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(ARG_STORE_IDX,storeIdx)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        storeIdx = savedInstanceState.getInt(ARG_STORE_IDX)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStoreDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        intent?.getIntExtra(ARG_STORE_IDX,-1)?.let {
            storeIdx = it
        }
        mViewModel = ViewModelProvider(this)[StoreDetailViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()

        mBinding.content.graph1.startAnimation()
        mBinding.content.graph2.startAnimation()
        mBinding.content.graph3.startAnimation()
    }

    private fun initView() {

        mBinding.header.shopName.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
            v.pivotX = v.width/2f
            v.pivotY = v.height/2f
        }

        mBinding.header.backButton setOnDebounceClickListener {
            onBackPressed()
        }

        mBinding.header.shopName.doOnLayout {
            it.pivotX = it.width.toFloat()
            it.pivotY = it.height.toFloat()
        }


        mBinding.content.timeLineRecyclerView.apply {
            adapter = TimeLineAdapter()
            addItemDecoration(LinearItemDecoration(12))
        }

        mBinding.content.more setOnDebounceClickListener {
            ProfitInfoDialog().show(supportFragmentManager,null)
        }

        mBinding.fund setOnDebounceClickListener {
            mViewModel.store.value?.storeIdx?.let {
                startActivity(FundingActivity.newIntent(this@StoreDetailActivity,it,mViewModel.store.value?.refundPercent ?: 0))
            }
        }

        mBinding.cheer setOnDebounceClickListener {
            startActivity(StoreCheerActivity.newIntent(this@StoreDetailActivity,mViewModel.store.value?.name ?: ""))
        }

        mBinding.content.menuRecyclerView.apply {
            adapter = StoreDetailAdapter()
        }

        mBinding.content.etcRecyclerView.apply {
            adapter = StoreDetailAdapter()
        }

//        WithdrawDialog.show(supportFragmentManager,5000,3500,"문명주") {
//            Timber.e("hi")
//        }
    }

    private fun observeViewModel() {
        mViewModel.apply {

            loading.observe(this@StoreDetailActivity) {
                if(it) showLoading() else hideLoading()
            }

            store.observe(this@StoreDetailActivity) {store->

                mBinding.header.coverImage.loadUrlAsync(store.thumbnail)

                if(store.leftDay > 0) {
                    mBinding.header.dueRemain1.text = "남은기간"
                    mBinding.header.dueRemain2.isVisible = true
                    mBinding.header.dueRemain2.text = "${store.leftDay} 일"
                }else {
                    mBinding.header.dueRemain1.text = "마감"
                    mBinding.header.dueRemain2.isVisible = false
                }

                mBinding.header.dueDate.text = "${store.dueDate} 마감"

                mBinding.header.progressText2.text = store.currentGoalPercent.toString() + "%"

                mBinding.header.archeiveAmmount.text = "목표금액 ${store.goalMoney.toMoney()}"

                mBinding.header.fundingProgress.progress = store.currentGoalPercent

                mBinding.content.info.text = buildSpannedString {
                    append("지금 투자 하면 ")
                    bold {
                        color(resources.getColor(R.color.colorPrimary)){
                            append("${store.refundPercent}%")
                        }
                    }
                    append(" 환급!")
                }

                when(RefundType.parseValue(store.refundPercent)) {
                    RefundType.REFUND_150 -> {
                        mBinding.content.graph1.progress = 0f
                        mBinding.content.graph2.progress = 0f
                        mBinding.content.graph3.progress = store.refundPercentOfPercent/100f
                    }
                    RefundType.REFUND_175-> {
                        mBinding.content.graph1.progress = 0f
                        mBinding.content.graph2.progress = store.refundPercentOfPercent/100f
                        mBinding.content.graph3.progress = 1f
                    }
                    RefundType.REFUND_200-> {
                        mBinding.content.graph1.progress = store.refundPercentOfPercent/100f
                        mBinding.content.graph2.progress = 1f
                        mBinding.content.graph3.progress = 1f
                    }
                }
                mBinding.content.graph1.startAnimation()
                mBinding.content.graph2.startAnimation()
                mBinding.content.graph3.startAnimation()


                Timber.e(store.funding.toString())
                store.funding?.let {funding->
                    if(funding.isWithdraw == 0) {
                        WithdrawDialog.show(supportFragmentManager,funding.fundingMoney,funding.profitMoney,store.name) {
                            mViewModel.onWithDraw(funding.storeIdx,funding.rewardMoney)
                        }
                    }
                }

            }
        }
    }
    
}