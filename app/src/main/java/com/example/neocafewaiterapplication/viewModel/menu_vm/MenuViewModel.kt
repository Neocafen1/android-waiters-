package com.example.neocafewaiterapplication.viewModel.menu_vm

import androidx.lifecycle.ViewModel
import com.example.neocafewaiterapplication.R
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

class MenuViewModel : ViewModel() {

    private val list = mutableListOf<AllModels>(
        AllModels.Menu(R.drawable.ic_coffee, "Кофе"),
        AllModels.Menu(R.drawable.ic_bubble_tea, "Чай"),
        AllModels.Menu(R.drawable.ic_soda, "Напитки"),
        AllModels.Menu(R.drawable.ic_ice_cream, "Десерты"),
        AllModels.Menu(R.drawable.ic_pizza, "Выпечка"),
    )

    fun getList(): MutableList<AllModels> {
        return list
    }

}