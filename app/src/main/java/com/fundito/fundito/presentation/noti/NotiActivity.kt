package com.fundito.fundito.presentation.noti

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.widget.hideLoading
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.common.widget.showLoading
import com.fundito.fundito.databinding.ActivityNotiBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.store.StoreDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by mj on 29, December, 2019
 */
class NotiActivity : DaggerAppCompatActivity(),HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivityNotiBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[NotiViewModel::class.java] }


    private var originalStatusBarColor = Color.WHITE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNotiBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
        observeViewModel()

        originalStatusBarColor = window.statusBarColor
        window.statusBarColor = Color.parseColor("#f7f8fa")
    }

    override fun onDestroy() {
        super.onDestroy()
        window.statusBarColor = originalStatusBarColor
    }

    private fun initView() {

        mBinding.toolbar.backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.recyclerView.apply {
            adapter = NotiAdapter {
                startActivity(StoreDetailActivity.newIntent(this@NotiActivity,it.storeIdx))
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    mBinding.shadow.isActivated = recyclerView.canScrollVertically(-1)
                }
            })
        }
    }

    private fun observeViewModel() {
        mViewModel.apply {
            loading.observe(this@NotiActivity) {
                if(it) showLoading() else hideLoading()
            }

        }
    }
}