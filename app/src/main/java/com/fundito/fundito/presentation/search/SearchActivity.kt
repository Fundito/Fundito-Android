package com.fundito.fundito.presentation.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.fundito.fundito.common.widget.setOnDebounceClickListener
import com.fundito.fundito.data.database.SearchItem
import com.fundito.fundito.databinding.ActivitySearchBinding
import com.fundito.fundito.di.module.ViewModelFactory
import com.fundito.fundito.presentation.store.StoreDetailActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.widget.textChanges

/**
 * Created by mj on 26, December, 2019
 */
@ExperimentalCoroutinesApi
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
        observeViewModel()
    }

    private fun initView() {

        mBinding.root setOnDebounceClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }

        mBinding.textField.apply {
            textChanges()
                .debounce(300L)
//                .distinctUntilChanged()
                .onEach {
                    mViewModel.onQueryChanged()
                }
                .launchIn(lifecycleScope)
        }

        mBinding.toolbar.backButton setOnDebounceClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mBinding.recyclerView.apply {
            adapter = SearchAdapter {
                mViewModel.onSearchItemSaved(SearchItem(storeIdx = it.storeIdx, name = it.name))
                startActivity(StoreDetailActivity.newIntent(this@SearchActivity,it.storeIdx))
            }

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    mBinding.shadow.isActivated = recyclerView.canScrollVertically(-1)
                }
            })

        }

        mBinding.recentRecyclerView.apply {
            adapter = SearchRecentAdapter({
                mViewModel.onSearchItemSaved(SearchItem(storeIdx = it.storeIdx, name = it.name))
                startActivity(StoreDetailActivity.newIntent(this@SearchActivity,it.storeIdx))
            },{
                mViewModel.onItemDeleted(it)
            })
        }

    }

    private fun observeViewModel() {
        mViewModel.apply {
            this.items.observe(this@SearchActivity) {
                if (it.isNotEmpty())
                    mBinding.result.text = it.size.toString() + "건"
                else
                    mBinding.result.text = ""
            }
        }
    }
    
}