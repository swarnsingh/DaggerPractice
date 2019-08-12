package com.swarn.daggerpractice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.swarn.daggerpractice.SessionManager
import com.swarn.daggerpractice.api.AuthAPI
import com.swarn.daggerpractice.model.User
import com.swarn.daggerpractice.vo.Resource
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Swarn Singh.
 */
class AuthViewModel @Inject constructor(authAPI: AuthAPI, sessionManager: SessionManager) : ViewModel() {

    private val TAG = AuthViewModel::class.java.name

    private var authAPI: AuthAPI
    private var sessionManager: SessionManager

    private var authUser: MediatorLiveData<Resource<User>> = MediatorLiveData()

    init {
        Log.d(TAG, "Auth View Model added")
        this.authAPI = authAPI
        this.sessionManager = sessionManager
    }

    fun authenticateWithId(userId: Int) {
        Log.d(TAG, "authenticateWithId: attempting to login.")
        sessionManager.authenticateWithId(queryUserId(userId))
    }

    private fun queryUserId(userId: Int): LiveData<Resource<User>> {
        return LiveDataReactiveStreams.fromPublisher(
            authAPI.getUser(userId)
                .onErrorReturn {
                    var errorUser = User()
                    errorUser.id = -1
                    return@onErrorReturn errorUser
                }
                .map {
                    if (it.id == -1) {
                        return@map Resource.error("Could not authenticated", null)
                    }
                    return@map Resource.authenticated(it)

                }.subscribeOn(Schedulers.io())
        )
    }

    fun observeAuthState(): LiveData<Resource<User>> {
        return sessionManager.getAuthUser()
    }
}