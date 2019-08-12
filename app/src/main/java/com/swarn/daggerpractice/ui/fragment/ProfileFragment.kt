package com.swarn.daggerpractice.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.swarn.daggerpractice.R
import com.swarn.daggerpractice.model.User
import com.swarn.daggerpractice.viewmodel.ProfileViewModel
import com.swarn.daggerpractice.viewmodel.ViewModelProviderFactory
import com.swarn.daggerpractice.vo.Resource.Status.AUTHENTICATED
import com.swarn.daggerpractice.vo.Resource.Status.ERROR
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class ProfileFragment : DaggerFragment() {

    private val TAG = ProfileFragment::class.java.canonicalName

    private lateinit var profileViewModel: ProfileViewModel

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private lateinit var emailTxtView: TextView

    private lateinit var usernameTxtView: TextView

    private lateinit var websiteTxtView: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "ProfileFragment is created...")

        emailTxtView = view.findViewById(R.id.email)

        usernameTxtView = view.findViewById(R.id.username)

        websiteTxtView = view.findViewById(R.id.website)

        profileViewModel = ViewModelProviders.of(this, providerFactory).get(ProfileViewModel::class.java)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        profileViewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)

        profileViewModel.getAuthenticatedUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it.status) {
                    AUTHENTICATED -> {
                        setUSerDetails(it.data)
                    }
                    ERROR -> {
                        setErrorDetails(it.message)
                    }
                }
            }
        })
    }

    private fun setErrorDetails(message: String?) {
        emailTxtView.text = message
        usernameTxtView.text = "Error"
        websiteTxtView.text = "Error"
    }

    private fun setUSerDetails(data: User?) {
        emailTxtView.text = data?.email
        usernameTxtView.text = data?.username
        websiteTxtView.text = data?.website
    }

}
