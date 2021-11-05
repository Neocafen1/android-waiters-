package com.example.neocafewaiterapplication.bottom_navigation.new_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.bottom_navigation.order.tables.TabelViewModel
import com.example.neocafewaiterapplication.databinding.FragmentNewOrderBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import com.example.neocafewaiterapplication.tools.clearAll


class NewOrderFragment : BaseFragment<FragmentNewOrderBinding>(), RecyclerItemClick {

    private val recyclerViewAdapter by lazy {MainRecyclerViewAdapter(this)}
    private val args by navArgs<NewOrderFragmentArgs>()
    private val shareViewModel: TabelViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
        }
        shareViewModel.getList().observe(viewLifecycleOwner, {
            recyclerViewAdapter.setList(it)
        })
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Table
        if (model.isFree) {
            findNavController().navigate(NewOrderFragmentDirections.actionNewOrderFragmentToNewOrderMenu(model.table_number))
        } else "Стол занят".clearAll(requireContext(), Toast.LENGTH_LONG)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewOrderBinding {
        return FragmentNewOrderBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        with(binding.include){
            notification.setOnClickListener { findNavController().navigate(NewOrderFragmentDirections.actionNewOrderFragmentToNotificationFragment3()) }
            user.setOnClickListener { findNavController().navigate(NewOrderFragmentDirections.actionNewOrderFragmentToUserFragment2()) }

        }
}
    }