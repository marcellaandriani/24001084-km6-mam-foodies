package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.category.CategoryDataSource
import com.example.foodiesapp.data.mapper.toCategories
import com.example.foodiesapp.data.model.Category
import com.example.foodiesapp.utils.ResultWrapper
import com.example.foodiesapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource,
) : CategoryRepository {
    override fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
    }
}
