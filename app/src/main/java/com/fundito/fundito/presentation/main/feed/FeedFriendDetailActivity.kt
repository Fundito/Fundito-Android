package com.fundito.fundito.presentation.main.feed

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.service.CurrentFundingResponse
import com.fundito.fundito.databinding.ActivityFeedFriendDetailBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.main.status.FundingOnGoingAdapter
import com.fundito.fundito.presentation.store.StoreDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendDetailActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    companion object {
        private const val ARG_FRIEND_IDX = "ARG_FRIEND_IDX"
        private const val ARG_FRIEND_NAME = "ARG_FRIEND_NAME"

        fun newIntent(context: Context, friendIdx: Int, friendName: String): Intent {
            return Intent(context, FeedFriendDetailActivity::class.java).apply {
                putExtra(ARG_FRIEND_IDX, friendIdx)
                putExtra(ARG_FRIEND_NAME, friendName)
            }
        }
    }

    private lateinit var mBinding: ActivityFeedFriendDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    val friendIdx: Int
        get() = intent?.getIntExtra(ARG_FRIEND_IDX, -1) ?: -1
    val friendName: String
    get() = intent?.getStringExtra(ARG_FRIEND_NAME) ?: ""

    private val mViewModel: FeedFriendDetailViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[FeedFriendDetailViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityFeedFriendDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        window.statusBarColor = Color.parseColor("#f2f2f2")

        initView()
        observeViewModel()
    }

    private fun initView() {
        mBinding.name.gravity = Gravity.LEFT
        mBinding.recyclerView.apply {
            adapter = FundingOnGoingAdapter({
                startActivity(StoreDetailActivity::class)
            }, false)
            addItemDecoration(LinearItemDecoration(1))
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    c.drawColor(Color.parseColor("#80eae8e8"))
                }
            })
        }

        mBinding.toolbar.backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.toolbar.toolbartitle.text = "$friendName 님의 투자현황"
        mBinding.name.text = buildSpannedString {
            bold { append(friendName) }
            append(" 님")
        }
    }

    private fun observeViewModel() {
        mViewModel.apply {

            items.observe(this@FeedFriendDetailActivity) {
                (mBinding.recyclerView.adapter as? FundingOnGoingAdapter)?.submitItems(
                    it.proceeding.map { CurrentFundingResponse(-1, it.storeName, it.remainingDays, it.progressPercent.toDouble()) }
                            + it.fail.map { CurrentFundingResponse(-1, it.storeName, -1, -1.0) }
                )
            }
        }
    }


}