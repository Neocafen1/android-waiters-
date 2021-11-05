package com.example.neocafewaiterapplication.bottom_navigation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.neocafewaiterapplication.databinding.FragmentMenuBinding
import com.example.neocafewaiterapplication.tools.BaseFragment
import com.example.neocafewaiterapplication.tools.delegates.RecyclerItemClick
import com.example.neocafewaiterapplication.tools.recycler_adapter.MainRecyclerViewAdapter
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels


class MenuFragment : BaseFragment<FragmentMenuBinding>(), RecyclerItemClick {

    private lateinit var recyclerAdapter: MainRecyclerViewAdapter
    private val viewModel: MenuViewModel by activityViewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAppBar()

        recyclerAdapter = MainRecyclerViewAdapter(this)

        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recyclerAdapter
        }
        recyclerAdapter.setList(viewModel.getList())


    }

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding {
        return FragmentMenuBinding.inflate(inflater)
    }

    override fun clickListener(model: AllModels) {
        model as AllModels.Menu
        findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToAllProductFragment(model.category)
        )

    }

    override fun setUpAppBar() {
        with(binding.include){
            notification.setOnClickListener { findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToNotificationFragment()) }
            binding.include.title.text = "Меню"
            user.setOnClickListener { findNavController().navigate(MenuFragmentDirections.actionMenuFragmentToUserFragment3()) }
        }
    }
}