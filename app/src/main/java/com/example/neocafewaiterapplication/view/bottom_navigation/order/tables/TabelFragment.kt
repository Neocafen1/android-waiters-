package com.example.neocafewaiterapplication.view.bottom_navigation.order.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentTabelBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.alert_dialog.CustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.orders_vm.TableViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class TabelFragment : BaseFragment<FragmentTabelBinding>(),RecyclerItemClick {

    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(this)}
    private val viewModel by viewModel<TableViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        viewModel.getTableList()
    }

    private fun setUpRecycler() {
        binding.recycler.apply{
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        viewModel.tableList.observe(viewLifecycleOwner){
            binding.recycler.visible()
            binding.progress.gone()
            recyclerAdapter.setList(it)
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentTabelBinding {
        return FragmentTabelBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        "SetUpAppBar in TabelFragment".logging()
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Table
        if (model.user != null){
            CustomAlertDialog("Хотите освободить стол", {changeTableStatus(model.qrCode)}).show(childFragmentManager, "TAG")
        }
    }

    private fun changeTableStatus(table:String){
        viewModel.changeStatusToFree(table)
        viewModel.statusChanged.observe(viewLifecycleOwner){
            viewModel.getTableList()
            DoneCustomAlertDialog("Стол освобожден").show(childFragmentManager,"TAG")
        }
    }
}