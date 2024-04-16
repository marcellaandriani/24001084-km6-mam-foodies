package com.example.foodiesapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.MenuRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel(){

    fun getMenus(category: String? = null) = menuRepository.getMenus(category).asLiveData(Dispatchers.IO)
    fun getCategories() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)
}
