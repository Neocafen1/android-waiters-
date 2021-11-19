package com.example.neocafewaiterapplication.viewModel.orders_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.logging
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.launch

class TableViewModel(private val repository: Repository) : ViewModel() {

    val tableList = MutableLiveData<MutableList<AllModels.Table>>()

    init {
        getTableList()
    }

    private fun getTableList() {
        viewModelScope.launch {
            "onCleared".logging()
            repository.getAllTables().let {
                if (it is Resource.Success) tableList.postValue(it.value)
            }
        }
    }

}