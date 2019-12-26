package com.fundito.fundito.presentation.store

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.databinding.ActivityStoreDetailBinding

/**
 * Created by mj on 26, December, 2019
 */
class StoreDetailActivity : AppCompatActivity(), HasDefaultViewModelProviderFactory {

    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory {
        return ViewModelFactory()
    }

    @Suppress("UNCHECKED_CAST")
    inner class ViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoreDetailViewModel() as T
        }
    }

    private lateinit var mBinding: ActivityStoreDetailBinding
    private lateinit var mViewModel: StoreDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityStoreDetailBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)


        mViewModel = ViewModelProvider(this)[StoreDetailViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
    }

    private fun initView() {
        mBinding.header.shopName.doOnLayout {
            it.pivotY = it.height.toFloat()
            it.pivotX = it.width/2f
        }
    }
    
}