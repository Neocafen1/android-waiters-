package com.example.neocafewaiterapplication.view.bottom_navigation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.databinding.FragmentOrderBinding
import com.example.neocafewaiterapplication.view.root.TabNavigationClass
import com.example.neocafewaiterapplication.view.utils.navigate
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderFragment : TabNavigationClass(R.layout.fragment_order) {

    private var _binding:FragmentOrderBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedTab: Tab

    override val containerId: Int = R.id.order_bottom_container_view
    override val tabs: List<Tab> = listOf(Tab("first", R.navigation.chairs_nav_graph), Tab("second", R.navigation.receipt_nav_graph))

    override fun tabSelected(tab: Tab) {
        selectedTab = tab
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        adjustGravity(binding.orderBottomNavigation)
        adjustWidth(binding.orderBottomNavigation)

        selectedTab = tabs[0]
        selectTab(tabs[0])
        binding.orderBottomNavigation.itemTextColor = null

        binding.orderBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.chairs_nav_graph -> {
                    if (selectedTab.name != tabs[0].name) selectTab(tabs[0])
                    true
                }
                R.id.receipt_nav_graph -> {
                    if (selectedTab.name != tabs[1].name) selectTab(tabs[1])
                    true
                }
                else -> false
            }
        }

        binding.notification.setOnClickListener { navigate(OrderFragmentDirections.actionOrderFragmentToNotificationFragment2()) }
        binding.user.setOnClickListener { navigate(OrderFragmentDirections.actionOrderFragmentToUserFragment()) }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun adjustGravity(v: View) {
        if (v is ViewGroup) {
            val vg = v as ViewGroup

            for (i in 0 until vg.childCount) {
                adjustGravity(vg.getChildAt(i))
            }
        }
    }

    private fun adjustWidth(nav: BottomNavigationView) {
        try {
            val menuViewField = nav.javaClass.getDeclaredField("mMenuView")
            menuViewField.isAccessible = true
            val menuView = menuViewField.get(nav)

            val itemWidth = menuView.javaClass.getDeclaredField("mActiveItemMaxWidth")
            itemWidth.isAccessible = true
            itemWidth.setInt(menuView, Integer.MAX_VALUE)
        } catch (e: NoSuchFieldException) {
            // TODO
        } catch (e: IllegalAccessException) {
            // TODO
        }
    }
}