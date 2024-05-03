package com.example.foodiesapp.data.datasource.category

import com.example.foodiesapp.data.source.network.model.category.CategoryResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoryResponse
}
