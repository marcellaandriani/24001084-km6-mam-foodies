package com.example.foodiesapp.data.datasource.menu

import com.example.foodiesapp.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodiesapp.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenus(category: String? = null): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
