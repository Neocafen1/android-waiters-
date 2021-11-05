package com.example.neocafewaiterapplication.tools.delegates

import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

interface SecondRecyclerItemClick {
    fun clickListener(method:String, model: AllModels.Product)
}