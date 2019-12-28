package com.fundito.fundito.presentation.main.feed

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.databinding.ActivityFeedFriendDetailBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.main.status.FundingOnGoingAdapter
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendDetailActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivityFeedFriendDetailBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel : FeedFriendDetailViewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[FeedFriendDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityFeedFriendDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
    }

    private fun initView() {
        mBinding.recyclerView.apply {
            adapter = FundingOnGoingAdapter {

            }
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    super.onDraw(c, parent, state)
                }
            })
        }
    }


}