package com.example.neocafewaiterapplication.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.tools.sealed_classes.AllModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: Repository) : ViewModel() {

    val token = MutableLiveData<Resource<AllModels.Token>>()

    fun getToken(number: Int, uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = async { repository.getToken(number, uid) }.await()
            token.postValue(response)
        }
    }

}