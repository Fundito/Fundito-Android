package com.fundito.fundito.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.util.startActivity
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.databinding.ActivitySearchBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.store.StoreDetailActivity
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by mj on 26, December, 2019
 */
class SearchActivity : DaggerAppCompatActivity(),HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivitySearchBinding

    @javax.inject.Inject
    lateinit var viewModelFactory : ViewModelFactory
    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel : SearchViewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySearchBinding.inflate(LayoutInflater.from(this))
        setContentView(mBinding.root)

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        initView()
    }

    private fun initView() {

        mBinding.toolbar.backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.recyclerView.apply {
            adapter = SearchAdapter {
                //TODO
                startActivity(StoreDetailActivity::class)
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    mBinding.shadow.isActivated = recyclerView.canScrollVertically(-1)
                }
            })

        }
    }
    
}