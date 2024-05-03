package com.example.foodiesapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun doLogin(
        email: String,
        password: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository.doLogin(email, password).asLiveData(Dispatchers.IO)
    }
}
