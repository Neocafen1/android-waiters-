package com.example.neocafewaiterapplication.bottom_navigation.new_order.new_order_menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neocafewaiterapplication.bottom_navigation.menu.MenuViewModel
import com.example.neocafewaiterapplication.databinding.FragmentNewOrderMenuBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels


class NewOrderMenu : BaseFragment<FragmentNewOrderMenuBinding>(),RecyclerItemClick {

    private val args:NewOrderMenuArgs by navArgs()
    private val viewModel:MenuViewModel by activityViewModels()
    private lateinit var recyclerViewAdapter: MainRecyclerViewAdapter

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewAdapter = MainRecyclerViewAdapter(this)
        binding.tableNumber.text = "Стол #${args.tableNumber}"
        setUpRecycler()

    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recyclerViewAdapter
        }
        recyclerViewAdapter.setList(viewModel.getList())
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewOrderMenuBinding {
        return FragmentNewOrderMenuBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Menu
        findNavController().navigate(NewOrderMenuDirections.actionNewOrderMenuToNewOrderProducts(model.category, args.tableNumber))
    }

    override fun setUpAppBar() {
        with(binding.appBar) {
            back.setOnClickListener { findNavController().navigateUp() }
            search.setOnClickListener { findNavController().navigate(NewOrderMenuDirections.actionNewOrderMenuToSearchFragment()) }
            notification.setOnClickListener { findNavController().navigate(NewOrderMenuDirections.actionNewOrderMenuToNotificationFragment3()) }
        }
    }

}