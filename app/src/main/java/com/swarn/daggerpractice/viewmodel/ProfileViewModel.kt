package com.swarn.daggerpractice.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.swarn.daggerpractice.SessionManager
import com.swarn.daggerpractice.model.User
import com.swarn.daggerpractice.vo.Resource
import javax.inject.Inject

/**
 * @author Swarn Singh.
 */
class ProfileViewModel @Inject constructor(sessionManager: SessionManager) : ViewModel() {

    private val TAG = ProfileViewModel::class.java.canonicalName

    private var sessionManager: SessionManager

    init {
        Log.d(TAG, "ProfileViewModel is ready")
        this.sessionManager = sessionManager
    }

    fun getAuthenticatedUser(): LiveData<Resource<User>> {
        return sessionManager.getAuthUser()
    }
}