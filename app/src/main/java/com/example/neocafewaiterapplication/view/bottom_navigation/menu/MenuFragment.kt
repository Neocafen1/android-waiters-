package com.example.neocafewaiterapplication.view.bottom_navigation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentMenuBinding
import com.example.neocafewaiterapplication.view.root.BaseFragment
import com.example.neocafewaiterapplication.view.utils.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.view.utils.navigate
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.ItemOffsetDecoration
import com.example.neocafewaiterapplication.view.utils.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import com.example.neocafewaiterapplication.viewModel.menu_vm.MenuViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MenuFragment : BaseFragment<FragmentMenuBinding>(), RecyclerItemClick {

    private val recyclerAdapter by lazy {MainRecyclerViewAdapter(this)}
    private val viewModel: MenuViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()
        setUpRecycler()
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recyclerAdapter
            addItemDecoration(ItemOffsetDecoration(requireContext(), R.dimen.item_offset))
        }
        recyclerAdapter.setList(viewModel.getList())
    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Menu
        navigate(MenuFragmentDirections.actionMenuFragmentToAllProductFragment(model.category))
    }

    override fun setUpAppBar() {
        with(binding.include){
            title.text = resources.getText(R.string.menu)
            notification.setOnClickListener { navigate(MenuFragmentDirections.actionMenuFragmentToNotificationFragment()) }
            user.setOnClickListener { navigate(MenuFragmentDirections.actionMenuFragmentToUserFragment3()) }
        }
    }
}