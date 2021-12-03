package com.example.neocafewaiterapplication.view.registration

import `in`.aabhasjindal.otptextview.OTPListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentOTPBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class OTPFragment : BaseFragment<FragmentOTPBinding>() {

    private val args: OTPFragmentArgs by navArgs()
    private var phoneNumber = 0
    private val viewModel by viewModel<RegistrationViewModel>()
    private val localDatabase: LocalDatabase by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        phoneNumber = args.phoneNumber
        otpListener()
        binding.confirm.setOnClickListener {
            localDatabase.saveUserNumber(phoneNumber)
            localDatabase.saveUserPassword(binding.otp.otp)
            getToken()
        }
        viewModel.isPasswordCorrect.observe(viewLifecycleOwner){
            with(binding){
                it?.let{
                    if (it){
                        confirm.apply {
                            isEnabled = true
                            background = ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.button_enable_custom_style
                            )
                        }
                    }else if(!it) {
                        typeCode.apply {
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                            text = "Неверный код*"
                        }
                    }
                }
            }
        }
    }

    private fun otpListener() {
        binding.otp.otpListener = object : OTPListener {
            override fun onInteractionListener() {
                binding.typeCode.apply {
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    text = "Введите код"
                }
                binding.confirm.apply {
                    isEnabled = false
                    background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.button_disable_custom_item
                    )
                }
            }

            override fun onOTPComplete(otp: String) {
                checkUser(otp)
            }
        }
    }

    private fun checkUser(password:String){ // Идет проверка пароля если пароль совпадает то только тогда проходит дальше (из за проблем в беке приходится так делайть)
        viewModel.checkUser(AllModels.UserData(phoneNumber.toString(), password))
    }

    //Получаем JWT токен
    private fun getToken(){
        binding.progress.visible()
        viewModel.getToken(phoneNumber, binding.otp.otp!!)
        viewModel.token.observe(viewLifecycleOwner){
            localDatabase.saveRefreshToken(it.refresh)
            localDatabase.saveAccessToken(it.access)
            FirebaseMessaging.getInstance().token.addOnSuccessListener { token ->
                saveFcmToken(token)
            }
        }
    }

    // Сохраняем FCM токен
    private fun saveFcmToken(token: String) {
        viewModel.saveFCM(AllModels.FCM_token(token, "W"))
        viewModel.isFcmSaved.observe(viewLifecycleOwner){
            if (it) {
                localDatabase.saveIsRegister(true)
                navigate(OTPFragmentDirections.actionOTPFragmentToBottomNavigationFragment2())
            }
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentOTPBinding {
        return FragmentOTPBinding.inflate(layoutInflater)
    }

    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { navController.navigateUp() }
    }
}