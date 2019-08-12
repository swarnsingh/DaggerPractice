package com.swarn.daggerpractice.di

import com.swarn.daggerpractice.di.auth.AuthModule
import com.swarn.daggerpractice.di.auth.AuthScope
import com.swarn.daggerpractice.di.auth.AuthViewModelsModule
import com.swarn.daggerpractice.di.main.MainFragmentBuildersModule
import com.swarn.daggerpractice.di.main.MainModule
import com.swarn.daggerpractice.di.main.MainScope
import com.swarn.daggerpractice.di.main.MainViewModelsModule
import com.swarn.daggerpractice.ui.activity.AuthActivity
import com.swarn.daggerpractice.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Swarn Singh.
 */
@Module
abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(
        modules = [AuthViewModelsModule::class, AuthModule::class]
    )
    abstract fun contributeAuthActivity(): AuthActivity


    @MainScope
    @ContributesAndroidInjector(
        modules = [MainFragmentBuildersModule::class,
            MainViewModelsModule::class, MainModule::class]
    )
    abstract fun contributeMainActivity(): MainActivity
}