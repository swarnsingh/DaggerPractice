package com.swarn.daggerpractice.di.auth

import com.swarn.daggerpractice.api.AuthAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * @author Swarn Singh.
 */

@Module
class AuthModule {

    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }
}