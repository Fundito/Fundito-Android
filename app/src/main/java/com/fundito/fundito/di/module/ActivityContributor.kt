package com.fundito.fundito.di.module

import com.fundito.fundito.di.ActivityScope
import com.fundito.fundito.presentation.charge.ChargeActivity
import com.fundito.fundito.presentation.main.MainActivity
import com.fundito.fundito.presentation.main.feed.FeedFriendDetailActivity
import com.fundito.fundito.presentation.noti.NotiActivity
import com.fundito.fundito.presentation.search.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by mj on 27, December, 2019
 */
@Module
abstract class ActivityContributor {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    abstract fun contributesMainActivity() : MainActivity

    @ContributesAndroidInjector(modules = [ChargeActivityModule::class])
    @ActivityScope
    abstract fun contributesChargeActivity() : ChargeActivity

    @ContributesAndroidInjector(modules = [FeedFriendDetailActivityModule::class])
    @ActivityScope
    abstract fun contributesFeedFriendDetailActivity() : FeedFriendDetailActivity

    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    @ActivityScope
    abstract fun contributesSearchActivity() : SearchActivity

    @ContributesAndroidInjector(modules = [NotiActivityModule::class])
    @ActivityScope
    abstract fun contributesNotiActivity() : NotiActivity
}