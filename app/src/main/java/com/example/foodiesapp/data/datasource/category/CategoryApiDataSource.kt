package com.example.foodiesapp.data.datasource.category

import com.example.foodiesapp.data.source.network.model.category.CategoryResponse
import com.example.foodiesapp.data.source.network.service.FoodiesApiService

class CategoryApiDataSource(private val service: FoodiesApiService) : CategoryDataSource {
    override suspend fun getCategories(): CategoryResponse {
        return service.getCategories()
    }
}
