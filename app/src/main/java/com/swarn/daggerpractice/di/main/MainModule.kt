package com.swarn.daggerpractice.di.main

import com.swarn.daggerpractice.adapter.PostsRecyclerAdapter
import com.swarn.daggerpractice.api.MainAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @author Swarn Singh.
 */

@Module
class MainModule {

    @MainScope
    @Provides
    fun providerAdapter(): PostsRecyclerAdapter {
        return PostsRecyclerAdapter()
    }

    @MainScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainAPI {
        return retrofit.create(MainAPI::class.java)
    }
}