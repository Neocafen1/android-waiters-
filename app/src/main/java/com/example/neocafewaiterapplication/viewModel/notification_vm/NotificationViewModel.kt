package com.example.neocafewaiterapplication.viewModel.notification_vm

import androidx.lifecycle.ViewModel
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels

class NotificationViewModel : ViewModel() {

    private val notificationList = mutableListOf<AllModels>(
        AllModels.Notification("Заказ готов", "Латте(3)/Капучино(1)", "13:15"),
        AllModels.Notification("Бариста принял заказ", "Латте(3)/Капучино(1)", "13:15"),
        AllModels.Notification("Закрытие счета", "Латте(3)/Капучино(1)", "13:15"),
        AllModels.Notification("Заказ готов", "Латте(3)/Капучино(1)", "13:15"),
    )

    fun getList() = notificationList

}