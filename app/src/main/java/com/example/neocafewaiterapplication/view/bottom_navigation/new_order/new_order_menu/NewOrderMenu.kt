package com.example.neocafewaiterapplication.view.bottom_navigation.new_order.new_order_menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentNewOrderMenuBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.ItemOffsetDecoration
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.viewModel.menu_vm.MenuViewModel


class NewOrderMenu : BaseFragment<FragmentNewOrderMenuBinding>(),RecyclerItemClick {

    private val args:NewOrderMenuArgs by navArgs()
    private val viewModel: MenuViewModel by activityViewModels()
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
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.item_offset))
        }
        recyclerViewAdapter.setList(viewModel.getList())
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewOrderMenuBinding {
        return FragmentNewOrderMenuBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Menu
        navigate(NewOrderMenuDirections.actionNewOrderMenuToNewOrderProducts(model.category, args.tableNumber))
    }

    override fun setUpAppBar() {
        with(binding.appBar) {
            back.setOnClickListener { navController.navigateUp() }
            notification.setOnClickListener { navigate(NewOrderMenuDirections.actionNewOrderMenuToNotificationFragment3()) }
        }
    }
}