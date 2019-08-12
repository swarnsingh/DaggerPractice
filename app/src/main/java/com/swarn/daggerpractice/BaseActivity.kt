package com.swarn.daggerpractice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.swarn.daggerpractice.ui.activity.AuthActivity
import com.swarn.daggerpractice.vo.Resource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * @author Swarn Singh.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {

    private val TAG = BaseActivity::class.java.canonicalName
    @Inject
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Resource.Status.LOADING -> {

                    }
                    Resource.Status.AUTHENTICATED -> {
                        Log.d(TAG, "onChanged: Login Success: " + it.data?.email)
                    }
                    Resource.Status.NOT_AUTHENTICATED -> {
                        navLoginScreen()
                    }
                    Resource.Status.ERROR -> {

                    }
                }
            }
        })
    }

    private fun navLoginScreen() {
        Intent(this, AuthActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}