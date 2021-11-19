package com.example.neocafewaiterapplication.view.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentRegisterNumberBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import com.example.neocafewaiterapplication.view.utils.*
import com.vicmikhailau.maskededittext.MaskedFormatter
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterNumberFragment : BaseFragment<FragmentRegisterNumberBinding>() {

    private val viewModel by viewModel<RegistrationViewModel>()

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
            checkUserNumber(formatter!!.toInt())
        }

    }

    private fun checkUserNumber(number: Int) {
        viewModel.checkNumber(number)
        viewModel.isNumberInBack.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(RegisterNumberFragmentDirections.actionRegisterNumberFragmentToOTPFragment(number))
            }
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterNumberBinding {
        return FragmentRegisterNumberBinding.inflate(inflater)
    }


    override fun setUpAppBar() {
        "SetUpAppBarFromRegisterNumberFragment".logging()
    }
}