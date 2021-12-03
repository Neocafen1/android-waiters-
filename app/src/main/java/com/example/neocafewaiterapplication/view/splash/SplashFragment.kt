package com.example.neocafewaiterapplication.view.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.databinding.FragmentSplashBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.core.KoinComponent

class SplashFragment : BaseFragment<FragmentSplashBinding>(), KoinComponent {

    private val localDatabase: LocalDatabase by inject()
    private val viewModel by sharedViewModel<RegistrationViewModel>()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextFragment()
    }

    private fun nextFragment() { // checking is User registered
        if (localDatabase.fetchRegistrationStatus()) {
            getToken()
        } else {
            navigate(SplashFragmentDirections.actionSplashFragmentToRegisterNumberFragment())
        }
//        getToken()
    }

    @SuppressLint("CommitPrefEdits")
    private fun getToken() {
        val password = localDatabase.fetchUserPassword()
        val number = localDatabase.fetchUserNumber()
        viewModel.getToken(number, password ?: "")
        binding.progress.visible()
//        viewModel.getToken(777555333, "0000")

        viewModel.token.observe(viewLifecycleOwner) {
            localDatabase.saveAccessToken(it.access)
            localDatabase.saveRefreshToken(it.refresh)
            navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavigationFragment())
        }
    }

    override fun setUpAppBar() {

    }
}