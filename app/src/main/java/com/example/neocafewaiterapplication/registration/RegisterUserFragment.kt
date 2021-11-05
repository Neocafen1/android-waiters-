package com.example.neocafewaiterapplication.registration

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentRegisterUserBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.Consts
import com.google.firebase.auth.FirebaseAuth


class RegisterUserFragment : BaseFragment<FragmentRegisterUserBinding>() {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterUserBinding {
        return FragmentRegisterUserBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            birthday.addTextChangedListener {
                if (it?.length!! >= 10 && name.text.length >= 3 && surname.text.length >= 3) {
                    button.apply {
                        isEnabled = true
                        background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.button_enable_custom_style
                        )
                    }
                } else {
                    button.apply {
                        isEnabled = false
                        background = ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.button_disable_custom_item
                        )
                    }
                }
            }
            button.setOnClickListener {
                val name = name.text.toString()
                val surname = surname.text.toString()
                val birthDay = birthday.text.toString()
                insertDataToSharedPreference(name, surname, birthDay)
                findNavController().navigate(RegisterUserFragmentDirections.actionRegisterUserFragmentToBottomNavigationFragment())
            }
        }

    }

    @SuppressLint("CommitPrefEdits")
    private fun insertDataToSharedPreference(name: String, surname: String, birthDay: String) {
        with(sharedPref!!.edit()) {
            putString(Consts.NAME, name)
            putString(Consts.SURNAME, surname)
            putString(Consts.BIRTHDAY, birthDay)
            putBoolean(Consts.REGISTRATION, true)

        }.apply()
    }

    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { findNavController().navigateUp() }    }
}