package com.example.neocafewaiterapplication.view.registration

import `in`.aabhasjindal.otptextview.OTPListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentOTPBinding
import com.example.neocafewaiterapplication.view.utils.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OTPFragment : Fragment() {

    private var binding: FragmentOTPBinding? = null
    private val args: OTPFragmentArgs by navArgs()
    private lateinit var firebaseCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var phoneNumber = 0
    private var id: String = ""


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPBinding.inflate(inflater, container, false)

        binding?.alertMessage?.text = "Код отправлен на номер ${args.phoneNumber}"
        binding?.backIcon?.setOnClickListener { findNavController().navigateUp() }
        phoneNumber = args.phoneNumber
        binding?.repeat?.setOnClickListener { signInWithCredential() }
        startTimer()
        with(binding!!) {
            alertMessage.text =
                "Введите код который дал администратор по номеру ${args.phoneNumber}"
            confirm.setOnClickListener { findNavController().navigate(OTPFragmentDirections.actionOTPFragmentToRegisterUserFragment(phoneNumber)) }
        }
        firebaseCallback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signIn(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                p0.message!!.showToast(requireContext(), Toast.LENGTH_LONG)
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                id = p0
                "Сообщение отправлено. Ждите".showToast(requireContext(), Toast.LENGTH_LONG)
            }
        }
        sendMessage()
        otpListener()

        return binding?.root
    }

    private fun sendMessage() { // Send Message to Firebase
        PhoneAuthOptions.newBuilder()
            .setActivity(requireActivity())
            .setPhoneNumber("+996$phoneNumber")
            .setTimeout(30L, TimeUnit.SECONDS)
            .setCallbacks(firebaseCallback)
            .build()
            .apply {
                PhoneAuthProvider.verifyPhoneNumber(this)
            }
    }

    private fun signIn(phone: PhoneAuthCredential) { // Заходит открывает следущее окно если номер в этом телефоне
        FirebaseAuth.getInstance().signInWithCredential(phone).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding?.otp?.setOTP(phone.smsCode ?: "000000")
                findNavController().navigate(OTPFragmentDirections.actionOTPFragmentToRegisterUserFragment(phoneNumber))
            }
        }

    }

    private fun otpListener() {
        binding?.otp?.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding?.repeat?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.button_disable_custom_item
                )
            }

            override fun onOTPComplete(otp: String) {
                binding?.repeat?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.button_enable_custom_style
                )
                binding?.repeat?.isEnabled = true
            }
        }
    }

    private fun signInWithCredential() {
        val phoneAuthCredential = PhoneAuthProvider.getCredential(id, binding?.otp?.otp!!)
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(OTPFragmentDirections.actionOTPFragmentToRegisterUserFragment(phoneNumber))
                }
            }
    }

    private fun startTimer() {
        object : CountDownTimer(30000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding?.repeat?.isEnabled = false
                binding?.repeat?.text =
                    "Отправить повторно ${millisUntilFinished / 1000}"
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                binding?.repeat?.text = "Отправить повторно"
                binding?.repeat?.isEnabled = true
                binding?.repeat?.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.button_enable_custom_style
                )
            }
        }.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}