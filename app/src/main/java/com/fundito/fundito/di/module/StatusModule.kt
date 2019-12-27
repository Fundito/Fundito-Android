package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.FragmentScope
import com.fundito.fundito.presentation.main.status.StatusViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 28, December, 2019
 */
@Module
abstract class StatusModule{
    @Binds
    @IntoMap
    @FragmentScope
    @ViewModelKey(StatusViewModel::class)
    abstract fun bindStatusViewModel(viewModel : StatusViewModel) : ViewModel
}