package com.example.foodiesapp.data.datasource.menu

import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(category: String? = null): MenuResponse
}