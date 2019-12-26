package com.fundito.fundito.presentation.store

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.LinearItemDecoration
import com.fundito.fundito.common.widget.setOnDebounceClickListener
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

    override fun onResume() {
        super.onResume()

        mBinding.content.graph1.startAnimation()
        mBinding.content.graph2.startAnimation()
        mBinding.content.graph3.startAnimation()
    }

    private fun initView() {

        mBinding.header.shopName.doOnLayout {
            it.pivotY = it.height.toFloat()
            it.pivotX = it.width/2f
        }



        mBinding.content.timeLineRecyclerView.apply {
            adapter = TimeLineAdapter().apply { submitItems(listOf("","","","")) }
            addItemDecoration(LinearItemDecoration(12))
        }

        mBinding.cheer setOnDebounceClickListener {
            startActivity(StoreCheerActivity::class)
        }

        ProfitInfoDialog().show(supportFragmentManager,null)

    }
    
}