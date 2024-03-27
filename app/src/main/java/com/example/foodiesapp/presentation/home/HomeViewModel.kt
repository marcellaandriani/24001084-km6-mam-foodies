package com.example.foodiesapp.presentation.home

import androidx.lifecycle.ViewModel
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.MenuRepository

class HomeViewModel (
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository
) : ViewModel(){

    fun getMenus() = menuRepository.getMenus()
    fun getCategories() = categoryRepository.getCategories()
}