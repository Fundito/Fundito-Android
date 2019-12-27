package com.fundito.fundito.di

import com.fundito.fundito.application.MainApplication
import com.fundito.fundito.di.module.ActivityContributor
import com.fundito.fundito.di.module.AppModule
import com.fundito.fundito.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by mj on 27, December, 2019
 */
@Component(modules = [
AndroidSupportInjectionModule::class,
AppModule::class,
ActivityContributor::class,
ViewModelModule::class
])
@AppScope
interface AppComponent : AndroidInjector<MainApplication> {
    override fun inject(instance: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(app: MainApplication) : Builder

        fun build() :AppComponent
    }
}