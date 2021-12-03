package com.example.neocafewaiterapplication.view.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentRegisterUserBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.viewModel.registration_vm.RegistrationViewModel
import org.koin.android.ext.android.inject


class RegisterUserFragment : BaseFragment<FragmentRegisterUserBinding>() {

    private val localDatabase: LocalDatabase by inject()
    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterUserBinding {
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
                            R.drawable.button_disable_custom_item)
                    }
                }
            }
            button.setOnClickListener {
                val name = name.text.toString()
                val surname = surname.text.toString()
                val birthDay = birthday.text.toString()
//                sendData(name, surname, birthDay)
            }
        }

    }

    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { navController.navigateUp() }
    }
}

//    @SuppressLint("CommitPrefEdits")
//    private fun sendData(name: String, surname: String, birthDay: String) {
//        var id = ""
        /* FirebaseMessaging.getInstance().token.addOnSuccessListener {
            viewModel.saveData(AllModels.UserInfo(args.number, name, surname, birthDay, it))
            id = it
        }
        viewModel.userCreated.observe(viewLifecycleOwner){
            if(it){
                getToken(args.number, id, name)
            }
        }
    }*/

        /* private fun getToken(number: Int, id: String, name: String) {
        viewModel.getToken(number, id)
        viewModel.token.observe(viewLifecycleOwner) {
            localDatabase.saveRefreshToken(it.refresh)
            localDatabase.saveAccessToken(it.access)
            localDatabase.saveUserNumber(args.number)
            localDatabase.saveUserName(name)
        }
        findNavController().navigate(RegisterUserFragmentDirections.actionRegisterUserFragmentToBottomNavigationFragment())
    }
*/
