package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.noti.NotiViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class NotiActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(NotiViewModel::class)
    abstract fun bindViewModel(viewModel : NotiViewModel) : ViewModel
}