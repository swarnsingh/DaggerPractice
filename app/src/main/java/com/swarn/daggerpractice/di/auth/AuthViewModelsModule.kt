package com.swarn.daggerpractice.di.auth

import androidx.lifecycle.ViewModel
import com.swarn.daggerpractice.di.ViewModelKey
import com.swarn.daggerpractice.viewmodel.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Swarn Singh.
 */
@Module
abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}