package com.example.neocafewaiterapplication.view.splash

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.databinding.FragmentSplashBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
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

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            nextFragment()
        }, 1500)
    }

    private fun nextFragment() { // checking is User registered
      /*  if (uid != null) {
            getToken()
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterNumberFragment())
        }*/

        getToken()
    }

    @SuppressLint("CommitPrefEdits")
    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            binding.progress.visible()
            viewModel.getToken(444333440, "0000")
        }

        viewModel.token.observe(viewLifecycleOwner) {
            localDatabase.saveAccessToken(it.access)
            localDatabase.saveRefreshToken(it.refresh)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavigationFragment())
        }
    }

    override fun setUpAppBar() {

    }
}