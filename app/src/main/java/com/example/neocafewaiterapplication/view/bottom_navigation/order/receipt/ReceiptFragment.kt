package com.example.neocafewaiterapplication.view.bottom_navigation.order.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentReceiptBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.OrderRecyclerAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.view.utils.visible
import com.example.neocafewaiterapplication.viewModel.orders_vm.ReceiptViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class ReceiptFragment : BaseFragment<FragmentReceiptBinding>(), RecyclerItemClick {

    private val recyclerAdapter by lazy {OrderRecyclerAdapter(this)}
    private val viewModel by sharedViewModel<ReceiptViewModel>()
    private var checkedText = ""
    private val appBar by lazy {activity?.findViewById(R.id.order_app_bar) as AppBarLayout}
    private val bottomNavigationView by lazy {activity?.findViewById(R.id.order_bottom_navigation) as BottomNavigationView}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAppBarAndBottomNavigation()
        setUpRecycler()
        with(binding){
            recycler.gone()
            progress.visible()
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                recyclerSetList(checkedId) //слушатель изменений chip
                viewModel.orders.postValue(AllModels.NeoOrder(mutableListOf<AllModels.Order>()))
            }

        }
        viewModel.orders.observe(viewLifecycleOwner){
            if (it.orders.isEmpty()){
                binding.empty.visible()
                binding.recycler.gone()
            }else{
                binding.empty.gone()
                binding.recycler.visible()
                recyclerAdapter.setList(it.orders)
            }
            binding.progress.gone()
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
    }

    private fun recyclerSetList(checkedId: Int) {
        checkedText = when (checkedId) { // по его id я достаю его имя
            R.id.ready -> "r"
            R.id.canceled -> "ca"
            R.id.new_order -> "new"
            R.id.in_process -> "iP"
            else -> "all"
        }
        viewModel.getOrders(checkedText) // получаем данные из бэка
    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptBinding {
        return FragmentReceiptBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) { // Открываем экран о продуктах и сразу мы должны закрыть Toolbar родителя
        hideAppBarAndBottomNavigation()
        navigate(ReceiptFragmentDirections.actionReceiptFragmentToProductFragment(model as AllModels.Order, checkedText))
    }

    private fun showAppBarAndBottomNavigation(){
        bottomNavigationView.visible()
        appBar.visible()
    }

    private fun hideAppBarAndBottomNavigation() {
        appBar.gone()
        bottomNavigationView.gone()
    }

    override fun setUpAppBar() {
        "SetUpAppBarFromReceiptFragment".logging()
    }

}