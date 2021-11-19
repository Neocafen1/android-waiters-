package com.example.neocafewaiterapplication.view.bottom_navigation.order.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentProductBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels


class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val args:ProductFragmentArgs by navArgs()
    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi(args.model)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi(order: AllModels.Order) {

        with(binding){
            receiptNumber.text = "#${order.id}"
            time.text = order.creationDate
            clientName.text = order.username
            recycler.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = recyclerAdapter
            }
//            recyclerAdapter.setList(order.orderItems)
        }

    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "ProductAppBar".logging()
    }

}