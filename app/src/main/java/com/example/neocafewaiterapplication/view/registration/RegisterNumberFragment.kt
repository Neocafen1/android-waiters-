package com.example.neocafewaiterapplication.view.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentRegisterNumberBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.*
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import com.vicmikhailau.maskededittext.MaskedFormatter
import org.koin.android.viewmodel.ext.android.viewModel


class RegisterNumberFragment : BaseFragment<FragmentRegisterNumberBinding>() {

    private val viewModel by viewModel<RegistrationViewModel>()

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.numberEditText.addTextChangedListener {
            viewModel.isNumberInBack.postValue(null)
            if (it?.length == 11) {
                binding.next.apply {
                    isEnabled = true
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_enable_custom_style
                    )
                }
            }else{
                binding.next.apply {
                    isEnabled = false
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_disable_custom_item
                    )
                }
            }
        }
        viewModel.isNumberInBack.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    val formatter = MaskedFormatter("###-###-###").formatString(binding.numberEditText.text.toString())?.unMaskedString?.toInt()
                    navigate(RegisterNumberFragmentDirections.actionRegisterNumberFragmentToOTPFragment(formatter!!))
                }else{
                    "Такого номера нету в базе данных".showSnackBar(binding.cardView)
                }
            }
        }

        binding.next.setOnClickListener {
            checkUserNumber()

        }

    }

    private fun checkUserNumber() {
        val formatter = MaskedFormatter("###-###-###").formatString(binding.numberEditText.text.toString())?.unMaskedString?.toInt()
        viewModel.checkNumber(formatter!!)
    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentRegisterNumberBinding {
        return FragmentRegisterNumberBinding.inflate(inflater)
    }


    override fun setUpAppBar() {
        "SetUpAppBarFromRegisterNumberFragment".logging()
    }
}