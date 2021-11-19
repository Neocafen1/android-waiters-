package com.example.neocafewaiterapplication.view.utils.delegates

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

interface SecondRecyclerItemClick {
    fun clickListener(method:String, model: AllModels.Product)
}