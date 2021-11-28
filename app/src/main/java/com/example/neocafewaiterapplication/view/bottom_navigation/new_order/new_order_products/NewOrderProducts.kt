package com.example.neocafewaiterapplication.view.bottom_navigation.new_order.new_order_products

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentNewOrderProductsBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.alert_dialog.ProductAlertDialog
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.AllProductsRecyclerAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.viewModel.new_order.NewOrderProductsViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel


class NewOrderProducts : BaseFragment<FragmentNewOrderProductsBinding>(), RecyclerItemClick {

    private val mapOfCategory = mutableMapOf(
        "Выпечка" to R.id.bakery, "Кофе" to R.id.coffee, "Чай" to R.id.tea,
        "Напитки" to R.id.drinks, "Десерты" to R.id.desserts
    )
    private val recyclerAdapter by lazy { AllProductsRecyclerAdapter(this) }
    private val viewModel by sharedViewModel<NewOrderProductsViewModel>()
    private val args: NewOrderProductsArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        updateVisibility(viewModel.getProductsTotalPrice())
        with(binding) {
            tableNumber.text = "Стол №${args.tableNumber}"
            takeOrder.setOnClickListener { navController.navigate(NewOrderProductsDirections.actionNewOrderProductsToFinalReceiptFragment(args.tableNumber)) }

            val viewId = mapOfCategory[args.category] // Через safe args узнаем какую категорию он выбрал
            if (viewId != null) {
                chipGroup.check(viewId) // enable данной категории
                recyclerSetList(viewId)
            }

            chipGroup.setOnCheckedChangeListener { _, checkedId ->
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

    override fun setUpAppBar() { // set clickListener to AppBar buttons
        with(binding.appBar) {
            back.setOnClickListener { navController.navigateUp() }
            notification.setOnClickListener { navController.navigate(NewOrderProductsDirections.actionNewOrderProductsToNotificationFragment3()) }
        }
    }

    private fun recyclerSetList(checkedId: Int) { // set sorted list to recycler view
        val checkedText = when (checkedId) { // using id i get category
            R.id.bakery -> "Выпечка"
            R.id.coffee -> "Кофе"
            R.id.tea -> "Чай"
            R.id.drinks -> "Напитки"
            R.id.desserts -> "Десерты"
            else -> ""
        }
        viewModel.productLiveData.observe(viewLifecycleOwner, {
            viewModel.sort(checkedText, it)
            binding.progress.gone()
            recyclerAdapter.setList(viewModel.sortedList, checkedText)
        })
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Product
        ProductAlertDialog(model).show(childFragmentManager, "TAG")
    }

    fun update(method: String, name: Int) { // если он меняет кол во продуктов то это
        recyclerAdapter.notifyDataSetChanged()
        plusUpdateTotalPrice(method, name)
    }

    @SuppressLint("SetTextI18n")
    private fun plusUpdateTotalPrice(method: String, name: Int) { // update TotalPrice
        viewModel.productLiveData.observe(viewLifecycleOwner) {
            viewModel.totalPriceAfterQuanityChange(it, method, name)
        }
        updateVisibility(viewModel.getProductsTotalPrice())

    }

    @SuppressLint("SetTextI18n")
    private fun updateVisibility(totalPrice: Int) { // change visibility of cardView by quantity of products
        with(binding){
            if (totalPrice > 0) {
                total.text = "${viewModel.totalPrice} c"
                takeOrder.visibility = View.VISIBLE
            } else takeOrder.visibility = View.GONE
        }
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNewOrderProductsBinding {
        return FragmentNewOrderProductsBinding.inflate(inflater)
    }
}