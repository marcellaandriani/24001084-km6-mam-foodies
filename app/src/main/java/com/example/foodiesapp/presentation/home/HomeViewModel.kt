package com.example.foodiesapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.MenuRepository
import com.example.foodiesapp.data.source.local.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    val isUsingGrid = MutableLiveData<Boolean>(userPreference.isUsingGridMode())

    fun changeGridMode() {
        val currentValue = isUsingGrid.value ?: false
        isUsingGrid.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getMenus(category: String? = null) =
        menuRepository.getMenus(category).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}