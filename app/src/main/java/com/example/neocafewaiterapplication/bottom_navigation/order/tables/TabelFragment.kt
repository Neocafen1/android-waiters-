package com.example.neocafewaiterapplication.bottom_navigation.order.tables

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentTabelBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.logging
import com.example.neocafewaiterapplication.tools.recycler_adapter.MainRecyclerViewAdapter

class TabelFragment : BaseFragment<FragmentTabelBinding>() {

    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}
    private val viewModel:TabelViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()

    }

    private fun setUpRecycler() {
        binding.recycler.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }

        viewModel.getList().observe(viewLifecycleOwner, {
            recyclerAdapter.setList(it)
        })

    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTabelBinding {
        return FragmentTabelBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "SetUpAppBar in TabelFragment".logging()
    }

}