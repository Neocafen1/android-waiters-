package com.example.neocafewaiterapplication.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafeteae1prototype.data.local.LocalDatabase
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentUserBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.alert_dialog.CustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.clickable
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.notClickable
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.ScheduleRecyclerAdapter
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.user_vm.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>() {

    private var isActive = false
    private val viewModel by viewModel<UserViewModel>()
    private val recyclerAdapter by lazy {ScheduleRecyclerAdapter()}
    private val localDatabase: LocalDatabase by inject()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpUi()
        viewModel.getUserInfo()

        viewModel.isUserInfoChanged.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    DoneCustomAlertDialog("Изменение сохранены").show(childFragmentManager, "TAG")
                    viewModel.isUserInfoChanged.postValue(null)
                }
            }
        }

        with(binding) {
            binding.logOut.setOnClickListener {
                CustomAlertDialog("Вы точно хотите выйти?", this@UserFragment::logOutFromAccount).show(childFragmentManager, "TAG")
            }

            name.apply { // через XML
                isFocusable = false
            }
            surname.apply {
                isFocusable = false
            }
            save.setOnClickListener {
                if (!isActive) {
                    name.isFocusableInTouchMode = true
                    surname.isFocusableInTouchMode = true
                    isActive = true
                    save.setImageResource(R.drawable.ic_done)
                } else {
                    isActive = false
                    save.setImageResource(R.drawable.ic_pencil)
                    name.isFocusable = false
                    surname.isFocusable = false
                    saveUserInfo(name.text.toString(), surname.text.toString())
                }
            }
        }
    }

    private fun logOutFromAccount() {
        localDatabase.clearData()
        requireActivity().finish()
    }

    private fun saveUserInfo(name: String, surname: String) {
        viewModel.changeNameAndSurname(name, surname)
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.schedule.observe(viewLifecycleOwner){
            recyclerAdapter.setList(it)
        }
    }

    private fun setUpUi() {
        viewModel.userInfo.observe(viewLifecycleOwner){
            with(binding){
                progress.gone()
                ratingCard.visible()
                emoji.visible()
                name.setText(it.first_name)
                surname.setText(it.last_name)
                birthday.text = it.birthDate ?: "null"
                number.text = it.number.toString()
            }
        }
        viewModel.rating.observe(viewLifecycleOwner){
            binding.rating.text = "#$it"
            binding.timeProgress.gone()
            binding.recycler.visible()
        }
    }


    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { navController.navigateUp() }
    }
}