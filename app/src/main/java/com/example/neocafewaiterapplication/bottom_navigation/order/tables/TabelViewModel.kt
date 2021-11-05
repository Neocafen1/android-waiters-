package com.example.neocafewaiterapplication.bottom_navigation.order.tables

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

class TabelViewModel : ViewModel() {

    private val list = MutableLiveData<MutableList<AllModels>>()

    private val tableList = mutableListOf<AllModels>(
        AllModels.Table(true, 1),
        AllModels.Table(true, 2),
        AllModels.Table(false, 3),
        AllModels.Table(true, 4),
        AllModels.Table(false, 5),
        AllModels.Table(false, 6)
    )

    init {
        list.value = tableList
    }

    fun getList(): MutableLiveData<MutableList<AllModels>> {
        return list
    }

}