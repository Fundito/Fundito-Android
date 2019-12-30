package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.FragmentScope
import com.fundito.fundito.presentation.main.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 28, December, 2019
 */
@Module
abstract class HomeModule{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel : HomeViewModel) : ViewModel
}