package com.swarn.daggerpractice

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.swarn.daggerpractice.model.User
import com.swarn.daggerpractice.vo.Resource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Swarn Singh.
 */
@Singleton
class SessionManager @Inject constructor() {
    private val TAG = SessionManager::class.java.canonicalName

    private var cachedUser = MediatorLiveData<Resource<User>>()

    fun authenticateWithId(source: LiveData<Resource<User>>) {
        if (cachedUser != null) {
            cachedUser.value = Resource.loading(null)
            cachedUser.addSource(source, Observer {
                cachedUser.value = it
                cachedUser.removeSource(source)
            })
        }
    }

    fun logout() {
        Log.d(TAG, "logOut: logging out...")
        cachedUser.value = Resource.logout()
    }

    fun getAuthUser(): LiveData<Resource<User>> {
        return cachedUser
    }
}