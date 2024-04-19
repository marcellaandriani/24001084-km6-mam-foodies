package com.example.foodiesapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiesapp.data.model.Profile
import com.example.foodiesapp.data.repository.UserRepository

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

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun doLogout() {
        repository.doLogout()
    }
    fun getCurrentUser() = repository.getCurrentUser()

}
