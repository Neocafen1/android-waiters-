package com.example.neocafewaiterapplication.view.bottom_navigation.new_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentNewOrderBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.showSnackBar
import com.example.neocafewaiterapplication.viewModel.new_order.NewOrderViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class NewOrderFragment : BaseFragment<FragmentNewOrderBinding>(), RecyclerItemClick {

    private val recyclerViewAdapter by lazy {MainRecyclerViewAdapter(this)}
    private val viewModel by viewModel<NewOrderViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewModel.getTableList()
    }

    private fun setUpRecyclerView() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerViewAdapter
        }
        viewModel.tableList.observe(viewLifecycleOwner){
            binding.progress.gone()
            recyclerViewAdapter.setList(it)
        }
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Table
        if (model.user == null) {
            navController.navigate(NewOrderFragmentDirections.actionNewOrderFragmentToNewOrderMenu(model.id))
        } else "Стол занят".showSnackBar(binding.cardView4)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewOrderBinding {
        return FragmentNewOrderBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        with(binding.include){
            notification.setOnClickListener { navController.navigate(NewOrderFragmentDirections.actionNewOrderFragmentToNotificationFragment3()) }
            user.setOnClickListener { navController.navigate(NewOrderFragmentDirections.actionNewOrderFragmentToUserFragment2()) }

        }
}
    }