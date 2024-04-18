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
            username = "Marcella Andriani Mahadewi",
            email = "marcellaadr22@gmail.com",
            password = "jajanterus",
            telephone = "0812345678910",
            profileImg = "https://avatars.githubusercontent.com/u/136700786?v=4"
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
}