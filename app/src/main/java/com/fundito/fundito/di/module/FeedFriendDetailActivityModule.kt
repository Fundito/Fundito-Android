package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.main.feed.FeedFriendDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by mj on 28, December, 2019
 */
@Module
abstract class FeedFriendDetailActivityModule {
    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(FeedFriendDetailViewModel::class)
    abstract fun bindViewModel(viewModel : FeedFriendDetailViewModel) : ViewModel
}