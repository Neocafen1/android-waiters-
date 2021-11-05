package com.example.neocafewaiterapplication.user

import androidx.lifecycle.ViewModel
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels

class UserViewModel: ViewModel() {

    val listWorkTime = mutableListOf<AllModels>(
        AllModels.WorkTime("Пн", "8:00", "22:00", "Morning"),
        AllModels.WorkTime("Вт", "8:00", "22:00", "Morning"),
        AllModels.WorkTime("Ср", "8:00", "22:00", "Morning"),
        AllModels.WorkTime("Чт", "8:00", "22:00", "Evening"),
        AllModels.WorkTime("Пт", "8:00", "22:00", "Evening"),
        AllModels.WorkTime("Пт", "8:00", "22:00", ""),
        AllModels.WorkTime("Пт", "8:00", "22:00", ""),
    )

}