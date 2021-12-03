package com.example.neocafewaiterapplication.viewModel.user_vm


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository): ViewModel() {

    val schedule = MutableLiveData<List<AllModels.WorkTime>>()
    val userInfo = MutableLiveData<AllModels.UserInfo>()
    val rating = MutableLiveData<Int>()
    val isUserInfoChanged = MutableLiveData<Boolean?>()
    

    init {
        getSchedule()
    }

    fun getUserInfo() {
        viewModelScope.launch {
            repository.getUserInfo().let {
                if (it is Resource.Success) userInfo.postValue(it.value)
            }
            repository.getRating().let {
                if (it is Resource.Success) rating.postValue(it.value)
            }
        }
    }

    fun changeNameAndSurname(name:String, surname:String){
        viewModelScope.launch {
            repository.changeNameAndSurname(name, surname).let {
                if (it is Resource.Success) isUserInfoChanged.postValue(it.value)
            }
        }
    }

    private fun getSchedule(){
        viewModelScope.launch {
            repository.getSchedule().let {
                if (it is Resource.Success){
                    this@UserViewModel.schedule.postValue(it.value)
                }
            }
        }
    }





}