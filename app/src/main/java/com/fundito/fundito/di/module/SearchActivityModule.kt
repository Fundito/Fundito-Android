package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class SearchActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindViewModel(viewModel : SearchViewModel) : ViewModel
}