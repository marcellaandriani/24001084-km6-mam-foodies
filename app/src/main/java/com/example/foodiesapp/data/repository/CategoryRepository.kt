package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.category.CategoryDataSource
import com.example.foodiesapp.data.model.Category

interface CategoryRepository {
    fun getCategories() : List<Category>
}

class  CategoryRepositoryImpl(private val dataSource: CategoryDataSource) : CategoryRepository{
    override fun getCategories(): List<Category> = dataSource.getCategories()

}