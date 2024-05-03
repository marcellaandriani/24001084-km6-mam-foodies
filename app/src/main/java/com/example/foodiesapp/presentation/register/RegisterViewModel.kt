package com.example.foodiesapp.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        username: String,
        password: String,
        email: String,
        numberPhone: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository
            .doRegister(
                username = username,
                email = email,
                password = password,
                numberPhone = numberPhone,
            )
            .asLiveData(Dispatchers.IO)
    }
}
