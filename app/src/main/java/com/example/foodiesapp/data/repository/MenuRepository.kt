package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.model.Menu

interface MenuRepository {
    fun getMenus() : List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource) : MenuRepository {
    override fun getMenus(): List<Menu> = dataSource.getMenus()
}