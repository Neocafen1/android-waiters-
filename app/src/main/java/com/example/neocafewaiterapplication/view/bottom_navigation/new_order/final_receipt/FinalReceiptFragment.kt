package com.example.neocafewaiterapplication.view.bottom_navigation.new_order.final_receipt

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.viewModel.new_order.NewOrderProductsViewModel
import com.example.neocafewaiterapplication.databinding.FragmentFinalReceiptBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.Consts
import com.example.neocafewaiterapplication.view.utils.alert_dialog.CustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.delegates.SecondRecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.FinalProductReceiptRecyclerAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FinalReceiptFragment : BaseFragment<FragmentFinalReceiptBinding>(), SecondRecyclerItemClick {

    private val recyclerAdapter by lazy { FinalProductReceiptRecyclerAdapter(this) }
    private val viewModel by sharedViewModel<NewOrderProductsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpSwipeCallback()
        printTotalPrice()
        binding.takeOrder.setOnClickListener {
            DoneCustomAlertDialog("Ваш заказ оформлен").show(childFragmentManager, "TAG")
            viewModel.finishList.clear()
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        viewModel.productLiveData.observe(viewLifecycleOwner, {
            recyclerAdapter.setList(viewModel.createFinishList(it))
        })
    }

    private fun setUpSwipeCallback() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder, ): Boolean {
                return false
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean,
            ) {
                RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.parseColor(Consts.RED))
                    .addActionIcon(R.drawable.ic_trash)
                    .create()
                    .decorate()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val product = viewModel.finishList.elementAt(position)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        CustomAlertDialog("Удалить ${product.title} из заказа?"
                        ) { deleteElementFromList(position) }.show(childFragmentManager, "TAG")
                    }
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.recycler)
    }

    private fun deleteElementFromList(position:Int){ // Сработает когда юзер через swipe удаляет многие элементы
        viewModel.discard(position)
        recyclerAdapter.notifyItemRemoved(position)
        printTotalPrice()
    }

    @SuppressLint("SetTextI18n")
    fun printTotalPrice(){ // Срабатывает при открытии чтоб узнать итоговую цену
        binding.totalPrice.text = "Итого. ${viewModel.getProductsTotalPrice()} сом"
    }

    @SuppressLint("SetTextI18n")
    override fun clickListener(method: String, model:AllModels.Product) { // Когда в recycler adapter изменяется кол во это трекается и изменяет итоговую цену
        if (method == "-"){
            viewModel.totalPrice -= model.price
        }else{
            viewModel.totalPrice += model.price
        }
        binding.totalPrice.text = "Итого. ${viewModel.totalPrice} сом"
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentFinalReceiptBinding {
        return FragmentFinalReceiptBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        with(binding){
            back.setOnClickListener { navController.navigateUp() }
            notification.setOnClickListener { navController.navigate(FinalReceiptFragmentDirections.actionFinalReceiptFragmentToNotificationFragment3())}
        }
    }
}