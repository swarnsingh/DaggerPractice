package com.swarn.daggerpractice.ui.activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.RequestManager
import com.swarn.daggerpractice.R
import com.swarn.daggerpractice.util.isInteger
import com.swarn.daggerpractice.viewmodel.AuthViewModel
import com.swarn.daggerpractice.viewmodel.ViewModelProviderFactory
import com.swarn.daggerpractice.vo.Resource.Status.*
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity() {

    private val TAG = AuthActivity::class.java.canonicalName

    private lateinit var authViewModel: AuthViewModel

    private lateinit var loginButton: Button

    private lateinit var userIdEditText: EditText

    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var logo: Drawable

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        userIdEditText = findViewById(R.id.user_id_input)

        loginButton = findViewById(R.id.login_button)

        progressBar = findViewById(R.id.progress_bar)

        authViewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel::class.java)

        loginButton.setOnClickListener {
            val value = userIdEditText.text.toString()
            if (!TextUtils.isEmpty(value)) {
                if (value.isInteger()) {
                    authViewModel.authenticateWithId(userIdEditText.text.toString().toInt())
                } else {
                    Toast.makeText(this, "Please Enter Integer only", Toast.LENGTH_SHORT).show()
                }
            }
        }

        setLogo()

        subscribeObservers()
    }

    private fun subscribeObservers() {

        authViewModel.observeAuthState().observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    LOADING -> {
                        showProgressBar(true)
                    }
                    AUTHENTICATED -> {
                        showProgressBar(false)
                        onLoginSuccess()
                        Log.d(TAG, "onChanged: Login Success: " + it.data?.email)
                    }
                    NOT_AUTHENTICATED -> {
                        showProgressBar(false)
                    }
                    ERROR -> {
                        showProgressBar(false)
                        Toast.makeText(this, it.message + "\n Please Enter number between 1 to 10", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        })
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun setLogo() {
        requestManager.load(logo)
            .into(findViewById(R.id.login_logo))
    }

    private fun onLoginSuccess() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
