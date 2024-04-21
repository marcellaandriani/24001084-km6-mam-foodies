package com.example.foodiesapp.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodiesapp.data.model.Profile
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    val profileData = MutableLiveData<Profile>()

    val isEditMode = MutableLiveData<Boolean>()

    init {
        profileData.value = Profile(
            username = "",
            email = "",
            password = "",
            profileImg = "https://i.pinimg.com/474x/a7/c8/16/a7c8160be69a3135f496df24290d000f.jpg"
        )
    }

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun updateUsername(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProfile(username = username).collect {
                _changeProfileResult.postValue(it)
            }
        }
    }

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    fun doChangePasswordByEmail(): Boolean {
        return repository.requestChangePasswordByEmail()
    }

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun doLogout() {
        repository.doLogout()
    }
    fun getCurrentUser() = repository.getCurrentUser()

}
