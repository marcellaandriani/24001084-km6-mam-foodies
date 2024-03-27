package com.example.foodiesapp.data.datasource.menu

import com.example.foodiesapp.data.model.Menu

interface MenuDataSource {
    fun getMenus(): List<Menu>
}