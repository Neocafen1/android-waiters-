package com.example.neocafewaiterapplication.bottom_navigation.order.receipt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentReceiptBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.logging
import com.example.neocafewaiterapplication.tools.recycler_adapter.OrderRecyclerAdapter
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class ReceiptFragment : BaseFragment<FragmentReceiptBinding>(), RecyclerItemClick {

    private lateinit var recyclerAdapter:OrderRecyclerAdapter
    private val viewModel by viewModels<ReceiptViewModel>()
    private val appBar by  lazy {activity?.findViewById(R.id.order_app_bar) as AppBarLayout}
    private val bottomNavigationView by lazy {activity?.findViewById(R.id.order_bottom_navigation) as BottomNavigationView}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAppBarAndBottomNavigation()
        recyclerAdapter = OrderRecyclerAdapter(this)
        setUpRecycler()
        with(binding){

            chipGroup.check(R.id.all) // enable данной категории
            recyclerSetList(R.id.all)

            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                recyclerSetList(checkedId) //слушатель изменений chip
            }
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
    }

    private fun recyclerSetList(checkedId: Int) {
        val checkedText = when (checkedId) { // по его id я достаю его имя
            R.id.all -> "Все"
            R.id.ready -> "Готово"
            R.id.canceled -> "Отменено"
            R.id.new_order -> "Новый"
            R.id.in_process -> "В процессе"
            else -> "Завершено"
        }
        viewModel.getList().observe(viewLifecycleOwner, {
            recyclerAdapter.setList(viewModel.sort(checkedText, it), checkedText)
        })
    }


    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentReceiptBinding {
        return FragmentReceiptBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) {
        hideAppBarAndBottomNavigation()
        findNavController().navigate(ReceiptFragmentDirections.actionReceiptFragmentToProductFragment(model))
    }

    private fun showAppBarAndBottomNavigation(){
        bottomNavigationView.visibility = View.VISIBLE
        appBar.visibility = View.VISIBLE
    }

    private fun hideAppBarAndBottomNavigation() {
        appBar.visibility = View.GONE
        bottomNavigationView.visibility = View.GONE
    }

    override fun setUpAppBar() {
        "SetUpAppBarFromReceiptFragment".logging()
    }

}