package com.example.neocafewaiterapplication.bottom_navigation.order.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentProductBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.logging
import com.example.neocafewaiterapplication.tools.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val args:ProductFragmentArgs by navArgs()
    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi(args.model as AllModels.Order)

    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi(order: AllModels.Order) {
        with(binding){
            time.text = order.time
            clientName.text = "Клиент: ${order.client}"
            receiptNumber.text = "#${order.orderNumber}"
            recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recyclerAdapter
            }
            recyclerAdapter.setList(order.list as MutableList<AllModels>)
        }

    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "ProductAppBar".logging()
    }

}