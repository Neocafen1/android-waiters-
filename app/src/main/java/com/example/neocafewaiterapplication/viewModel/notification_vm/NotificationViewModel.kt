package com.example.neocafewaiterapplication.viewModel.notification_vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.launch

class NotificationViewModel(private val repository: Repository) : ViewModel() {

    val notificationList = MutableLiveData<MutableList<AllModels.Notification>>()
    val isNotificationsDeleted = MutableLiveData<Boolean?>()


    fun getNotificationList(){
        viewModelScope.launch {
            repository.getNotification().let {
                if (it is Resource.Success) notificationList.postValue(it.value)
            }
        }
    }

    fun deleteNotificationItem(id:Int){
        viewModelScope.launch {
            repository.deleteNotifications(id)
        }
    }

    fun deleteAllNotifications(){
        viewModelScope.launch {
            repository.deleteAllNotifications().let {
                if(it is Resource.Success)  isNotificationsDeleted.postValue(it.value)
            }
        }
    }
}