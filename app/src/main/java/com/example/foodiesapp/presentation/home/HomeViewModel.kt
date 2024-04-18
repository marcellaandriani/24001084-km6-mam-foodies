package com.example.foodiesapp.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel() {

    val isUsingGrid = MutableLiveData<Boolean>(false)

    fun changeGridMode() {
        val currentValue = isUsingGrid.value ?: false
        isUsingGrid.postValue(!currentValue)
    }

    fun getMenus(category: String? = null) =
        menuRepository.getMenus(category).asLiveData(Dispatchers.IO)

    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}