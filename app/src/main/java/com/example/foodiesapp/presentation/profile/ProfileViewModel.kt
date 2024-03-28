package com.example.foodiesapp.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodiesapp.data.model.Profile

class ProfileViewModel : ViewModel() {

    val profileData = MutableLiveData(
        Profile(
            username = "Marcella Andriani Mahadewi",
            email = "marcellaadr22@gmail.com",
            password = "jajanterus",
            telephone = "0812345678910",
            profileImg = "https://avatars.githubusercontent.com/u/136700786?v=4"
        )
    )

    val isEditMode = MutableLiveData<Boolean>()

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}
