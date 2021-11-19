package com.example.neocafewaiterapplication.viewModel.registration_vm


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.neocafeteae1prototype.data.models.Resource
import com.example.neocafewaiterapplication.repository.Repository
import com.example.neocafewaiterapplication.view.utils.sealed_classes.AllModels
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrationViewModel(private val repository: Repository) : ViewModel() {

    val token = MutableLiveData<AllModels.Token>()
    val isNumberInBack = MutableLiveData<Boolean>()
    val userCreated = MutableLiveData<Boolean>()

    fun getToken(number: Int, uid: String) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getToken(number, uid).let {
                if (it is Resource.Success) this@RegistrationViewModel.token.postValue(it.value)
            }
        }
    }

    fun checkNumber(number:Int){
        viewModelScope.launch {
            repository.checkNumber(number).let {
                if (it.isSuccessful) isNumberInBack.postValue(it.body())
            }
        }
    }

    fun saveData(userInfo: AllModels.UserInfo){
        viewModelScope.launch {
            repository.saveUserInfo(userInfo).let {
                if (it is Resource.Success){
                    userCreated.postValue(true)
                }
            }
        }
    }
}