package com.example.neocafewaiterapplication.registration

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentRegisterNumberBinding
import com.example.neocafewaiterapplication.tools.*
import com.vicmikhailau.maskededittext.MaskedFormatter


class RegisterNumberFragment : BaseFragment<FragmentRegisterNumberBinding>() {
    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterNumberBinding {
        return FragmentRegisterNumberBinding.inflate(inflater)
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberEditText.addTextChangedListener {
            if (it?.length == 11) {
                binding.next.apply {
                    isEnabled = true
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_enable_custom_style
                    )
                }
            } else if (it?.length!! >= 1) {
                binding.numberCode.visible()
            } else {
                binding.next.apply {
                    isEnabled = false
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_disable_custom_item
                    )
                }
            }
        }

        binding.next.setOnClickListener {
            val formatter = MaskedFormatter("###-###-###").formatString(binding.numberEditText.text.toString())?.unMaskedString
            formatter?.clearAll(requireContext(), Toast.LENGTH_LONG)
            insertDataToSharedPreference(formatter!!)
            findNavController().navigate(RegisterNumberFragmentDirections.actionRegisterNumberFragmentToOTPFragment(formatter))
        }

    }

    @SuppressLint("CommitPrefEdits")
    private fun insertDataToSharedPreference(number:String){
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putString(Consts.PHONE_NUMBER, number)
        }.apply()
    }

    override fun setUpAppBar() {
        "SetUpAppBarFromRegisterNumberFragment".logging()
    }
}