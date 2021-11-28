package com.example.neocafewaiterapplication.view.bottom_navigation.order.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentProductBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.orders_vm.ReceiptViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val args:ProductFragmentArgs by navArgs()
    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}
    private val viewModel by sharedViewModel<ReceiptViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi(args.model)
        setUpRecycler()

        if (args.model.status == "r"){
            binding.closeOrder.visible()
        }


    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        recyclerAdapter.setList(args.model.orderItems)
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUi(order: AllModels.Order) {
        with(binding){
            receiptNumber.text = "#${order.id}"
            time.text = "Открыт в ${order.time}"
            clientName.text = "Клиент: ${order.username}"
            totalPrice.text = "${order.total_sum} сом"
            closeOrder.setOnClickListener { closeOrder(order.id) }
        }
    }

    private fun closeOrder(id: Int) {
        viewModel.closeOrder(id)
        viewModel.isOrderClosed.observe(viewLifecycleOwner){
            if (it){
                viewModel.orders.postValue(AllModels.NeoOrder(mutableListOf<AllModels.Order>()))
                viewModel.getOrders(args.status)
                DoneCustomAlertDialog("Заказ успешно закрыт").show(childFragmentManager, "TAG")
            }
        }
    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentProductBinding {
        return FragmentProductBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "ProductAppBar".logging()
    }

}