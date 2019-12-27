package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.charge.ChargeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class ChargeActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(ChargeViewModel::class)
    abstract fun bindViewModel(viewModel : ChargeViewModel) : ViewModel

}