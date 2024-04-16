package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.mapper.toMenu
import com.example.foodiesapp.data.mapper.toMenus
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.utils.ResultWrapper
import com.example.foodiesapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(category : String? = null) : Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource
) : MenuRepository {
    override fun getMenus(category : String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenus(category).data.toMenus() }
    }
}