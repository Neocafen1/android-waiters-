package com.example.neocafewaiterapplication.view.root

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentBottomNavigationBinding


class BottomNavigationFragment : TabNavigationClass(R.layout.fragment_bottom_navigation) {
    override val containerId: Int = R.id.bottom_container_view
    private lateinit var selectedTab: Tab
    override val tabs: List<Tab> = listOf(Tab("first", R.navigation.order_nav_graph), Tab("second", R.navigation.menu_nav_graph),
        Tab("third", R.navigation.new_order_nav_graph))

    private var _binding:FragmentBottomNavigationBinding? = null
    private val binding get() = _binding!!



    override fun tabSelected(tab: Tab) {
        selectedTab = tab
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomNavigationBinding.inflate(inflater, container, false)

        selectedTab = tabs[0]
        selectTab(tabs[0])

        binding.bottomNavigation.itemIconTintList = null // Насторивает цвет как и в drawable
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.order_nav_graph -> {
                    if (selectedTab.name != tabs[0].name) selectTab(tabs[0])
                    true
                }
                R.id.menu_nav_graph -> {
                    if (selectedTab.name != tabs[1].name) selectTab(tabs[1])
                    true
                }
                R.id.new_order_nav_graph -> {
                    if (selectedTab.name != tabs[2].name) selectTab(tabs[2])
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}