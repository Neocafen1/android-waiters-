package com.example.neocafewaiterapplication.tools.delegates

import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

interface RecyclerItemClick {
    fun clickListener(model: AllModels)
}