package com.example.foodiesapp.data.mapper

import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu(): Menu {
    return Menu(
        imgUrl = this?.imageUrl.orEmpty(),
        name = this?.nama.orEmpty(),
        formatPrice = this?.hargaFormat.orEmpty(),
        price = this?.harga?.toDouble() ?: 0.0, // Changed to toDoubleOrNull()
        description = this?.detail.orEmpty(),
        address = this?.alamatResto.orEmpty(),
        mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77",
    )
}

fun Collection<MenuItemResponse>?.toMenus(): List<Menu> {
    return this?.map { it.toMenu() } ?: emptyList()
}
