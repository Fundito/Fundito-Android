package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.di.FragmentScope
import com.fundito.fundito.presentation.main.MainViewModel
import com.fundito.fundito.presentation.main.home.HomeFragment
import com.fundito.fundito.presentation.main.status.StatusFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel : MainViewModel) : ViewModel

    @ContributesAndroidInjector(modules=[StatusModule::class])
    @FragmentScope
    abstract fun contributesStatusBackgroundFragment() : StatusFragment

    @ContributesAndroidInjector(modules = [HomeModule::class])
    @FragmentScope
    abstract fun contributesHomeFragment() : HomeFragment

}