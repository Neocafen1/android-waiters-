package com.example.neocafewaiterapplication.view.bottom_navigation.order.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentTabelBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.viewModel.orders_vm.TableViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TabelFragment : BaseFragment<FragmentTabelBinding>() {

    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}
    private val viewModel by viewModel<TableViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
    }

    private fun setUpRecycler() {
        binding.recycler.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        viewModel.tableList.observe(viewLifecycleOwner){
            recyclerAdapter.setList(it)
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentTabelBinding {
        return FragmentTabelBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "SetUpAppBar in TabelFragment".logging()
    }

}