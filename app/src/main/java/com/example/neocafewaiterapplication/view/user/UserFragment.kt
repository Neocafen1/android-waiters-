package com.example.neocafewaiterapplication.view.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentUserBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.viewModel.user_vm.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserFragment : BaseFragment<FragmentUserBinding>() {

    private var isActive = false
    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}
    private val viewModel by viewModel<UserViewModel>()

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentUserBinding {
        return FragmentUserBinding.inflate(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpUi()

        with(binding) {
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
                    saveUserInfo()
                }
            }
        }
    }

    private fun saveUserInfo() {
        DoneCustomAlertDialog("Изменение сохранены").show(childFragmentManager, "TAG")
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        viewModel.schedule.observe(viewLifecycleOwner){
            recyclerAdapter.setList(it)
        }
    }

    private fun setUpUi() {
        viewModel.userInfo.observe(viewLifecycleOwner){
            with(binding){
                progress.gone()
                name.setText(it.first_name)
                surname.setText(it.last_name)
                birthday.text = it.birthDate ?: "null"
                number.text = it.number.toString()
            }
        }
        viewModel.rating.observe(viewLifecycleOwner){
            binding.rating.text = "#$it"
        }
    }


    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { navController.navigateUp() }
    }
}