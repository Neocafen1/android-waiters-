package com.example.neocafewaiterapplication.view.notification

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentNotificationBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.Consts
import com.example.neocafewaiterapplication.view.utils.alert_dialog.CustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.alert_dialog.DoneCustomAlertDialog
import com.example.neocafewaiterapplication.view.utils.gone
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.viewModel.notification_vm.NotificationViewModel
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import org.koin.android.viewmodel.ext.android.viewModel


class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(null)}
    private val viewModel by viewModel<NotificationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotificationList()
        with(binding){
            setUpRecycler()
            setUpSwipeCallback()
            clearAll.setOnClickListener {
                viewModel.deleteAllNotifications()
            }
        }

        viewModel.isNotificationsDeleted.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    DoneCustomAlertDialog("Данные удалены")
                    viewModel.isNotificationsDeleted.postValue(null)
                    viewModel.getNotificationList()
                }
            }
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
        viewModel.notificationList.observe(viewLifecycleOwner){
            binding.progress.gone()
            if (it.isEmpty()){
                binding.clearAll.gone()
            }else{
                recyclerAdapter.setList(it)
            }
        }
    }

    private fun setUpSwipeCallback() {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder, ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean, ) {
                RecyclerViewSwipeDecorator.Builder(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(Color.parseColor(Consts.RED))
                    .addActionIcon(R.drawable.ic_trash)
                    .create()
                    .decorate()

                super.onChildDraw(c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        viewModel.notificationList.value?.let {
                            viewModel.deleteNotificationItem(it[position].id)
                            it.removeAt(position)
                            recyclerAdapter.notifyItemRemoved(position)
                        }
                    }
                }
            }
        }
        ItemTouchHelper(callback).attachToRecyclerView(binding.recycler)
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentNotificationBinding {
        return FragmentNotificationBinding.inflate(inflater)
    }

    override fun setUpAppBar() {
        binding.backIcon.setOnClickListener { navController.navigateUp() }
    }

}