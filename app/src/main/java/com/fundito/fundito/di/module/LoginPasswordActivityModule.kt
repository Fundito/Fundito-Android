package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.login.LoginPasswordViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class LoginPasswordActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(LoginPasswordViewModel::class)
    abstract fun bindViewModel(viewModel : LoginPasswordViewModel) : ViewModel
}