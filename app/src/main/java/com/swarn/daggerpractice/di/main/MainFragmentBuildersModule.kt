package com.swarn.daggerpractice.di.main

import com.swarn.daggerpractice.ui.fragment.PostsFragment
import com.swarn.daggerpractice.ui.fragment.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Swarn Singh.
 */
@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    abstract fun contributePostsFragment(): PostsFragment
}