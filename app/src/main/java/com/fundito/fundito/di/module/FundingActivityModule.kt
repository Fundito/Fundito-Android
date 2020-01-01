package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.di.FragmentScope
import com.fundito.fundito.presentation.funding.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Named

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class FundingActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(FundingViewModel::class)
    abstract fun bindViewModel(viewModel : FundingViewModel) : ViewModel

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesFundingInputFragment() : FundingInputFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesFundingProgressFragment() : FundingProgressFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributesFundingCompleteFragment() : FundingCompleteFragment

}

@Module
class FundingActivityProvideModule {
    @Provides
    @Named("FundingActivity_storeIdx")
    @ActivityScope
    fun provideStoreIndex(activity: FundingActivity) = activity.storeId
}