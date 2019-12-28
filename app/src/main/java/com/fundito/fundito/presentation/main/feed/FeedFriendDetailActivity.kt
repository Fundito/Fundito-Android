package com.fundito.fundito.presentation.main.feed

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.databinding.ActivityFeedFriendDetailBinding
import com.fundito.fundito.di.module.ViewModelFactory
import javax.inject.Inject

/**
 * Created by mj on 26, December, 2019
 */
class FeedFriendDetailActivity : AppCompatActivity(), HasDefaultViewModelProviderFactory {

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
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel
    }


}