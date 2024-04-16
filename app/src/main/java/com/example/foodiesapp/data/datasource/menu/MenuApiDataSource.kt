package com.example.foodiesapp.data.datasource.menu

import com.example.foodiesapp.data.source.network.model.menu.MenuResponse
import com.example.foodiesapp.data.source.network.service.FoodiesApiService

class MenuApiDataSource (private val service : FoodiesApiService) : MenuDataSource {
    override suspend fun getMenus(category: String?): MenuResponse {
        return service.getMenus(category)
    }
}