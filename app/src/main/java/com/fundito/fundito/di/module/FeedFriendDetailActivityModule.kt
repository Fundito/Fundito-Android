package com.fundito.fundito.di.module

import androidx.lifecycle.ViewModel
import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.main.feed.FeedFriendDetailActivity
import com.fundito.fundito.presentation.main.feed.FeedFriendDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

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
@Module
class FeedFriendDetailActivityModuleProvider {
    @Provides
    @Named("FeedFriendDetailActivityModule_friendIdx")
    @ActivityScope
    fun provideViewModel(activity: FeedFriendDetailActivity) : Int {
        return activity.friendIdx
    }
}