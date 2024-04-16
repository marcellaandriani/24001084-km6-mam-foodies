package com.example.foodiesapp.data.mapper

import com.example.foodiesapp.data.model.Category
import com.example.foodiesapp.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory(): Category {
    return Category(
        name = this?.nama.orEmpty(),
        imageUrl = this?.imageUrl.orEmpty()
    )
}

fun Collection<CategoryItemResponse>?.toCategories(): List<Category> {
    return this?.map { it.toCategory() } ?: emptyList()
}
