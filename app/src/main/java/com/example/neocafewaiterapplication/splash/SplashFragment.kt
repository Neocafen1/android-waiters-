package com.example.neocafewaiterapplication.splash

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.databinding.FragmentSplashBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.Consts
import com.example.neocafewaiterapplication.tools.gone
import com.example.neocafewaiterapplication.tools.visible
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    private val uid = FirebaseAuth.getInstance().uid
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
        if (uid != null) {
            getToken()
        } else {
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToRegisterNumberFragment())
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun getToken() {
        binding.progress.visible()
        val number = sharedPref?.getString(Consts.PHONE_NUMBER, "0")
        viewModel.getToken(777888999, "4477")
        viewModel.token.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progress.gone()
                    sharedPref!!.edit().apply {
                        putString(Consts.ACCESS, it.value.access)
                        putString(Consts.REFRESH, it.value.refresh)
                    }
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToBottomNavigationFragment())
                }
            }
        }
    }

    override fun setUpAppBar() {

    }
}