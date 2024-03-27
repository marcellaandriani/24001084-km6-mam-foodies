package com.example.foodiesapp.data.datasource.category

import com.example.foodiesapp.data.model.Category

interface CategoryDataSource {
    fun getCategories(): List<Category>
}