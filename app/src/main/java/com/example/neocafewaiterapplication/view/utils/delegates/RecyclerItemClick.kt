package com.example.neocafewaiterapplication.view.utils.delegates

import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

interface RecyclerItemClick {
    fun clickListener(model: AllModels)
}