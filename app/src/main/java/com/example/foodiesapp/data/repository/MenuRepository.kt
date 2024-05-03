package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.mapper.toMenus
import com.example.foodiesapp.data.model.Cart
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutItemPayload
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodiesapp.utils.ResultWrapper
import com.example.foodiesapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(
        profile: String,
        menus: List<Cart>,
        totalPrice: Int,
    ): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(
    private val dataSource: MenuDataSource,
) : MenuRepository {
    override fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow { dataSource.getMenus(category).data.toMenus() }
    }

    override fun createOrder(
        profile: String,
        menus: List<Cart>,
        totalPrice: Int,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    total = totalPrice,
                    username = profile,
                    orders =
                        menus.map {
                            CheckoutItemPayload(
                                nama = it.menuName,
                                qty = it.itemQuantity,
                                catatan = it.itemNotes.orEmpty(),
                                harga = it.menuPrice.toInt(),
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
