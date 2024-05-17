package com.example.foodiesapp.data.repository

import app.cash.turbine.test
import com.example.foodiesapp.data.datasource.menu.MenuDataSource
import com.example.foodiesapp.data.model.Cart
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodiesapp.data.source.network.model.menu.MenuItemResponse
import com.example.foodiesapp.data.source.network.model.menu.MenuResponse
import com.example.foodiesapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var dataSource: MenuDataSource

    private lateinit var repository: MenuRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = MenuRepositoryImpl(dataSource)
    }

    @Test
    fun getMenus_success() {
        val m1 =
            MenuItemResponse(
                nama = "Menu 1",
                imageUrl = "https://example.com/image1.jpg",
                hargaFormat = "$10",
                harga = 10000,
                detail = "example 1",
                alamatResto = "example restaurant 1",
            )
        val m2 =
            MenuItemResponse(
                nama = "Menu 2",
                imageUrl = "https://example.com/image2.jpg",
                hargaFormat = "$14",
                harga = 14000,
                detail = "example 2",
                alamatResto = "example restaurant 2",
            )
        val mockListMenu = listOf(m1, m2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { dataSource.getMenus() } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getMenus() }
            }
        }
    }

    @Test
    fun getMenus_loading() {
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns null

        runTest {
            coEvery { dataSource.getMenus() } returns mockResponse
            repository.getMenus().map {
                delay(100)
                it
            }.test {
                delay(110)
                assertTrue(expectMostRecentItem() is ResultWrapper.Loading)
                coVerify { dataSource.getMenus() }
            }
        }
    }

//    @Test
//    fun getMenus_error() {
//    }

    @Test
    fun getMenus_empty() {
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns emptyList()

        runTest {
            coEvery { dataSource.getMenus() } returns mockResponse
            repository.getMenus().test {
                delay(100)
                assertTrue(expectMostRecentItem() is ResultWrapper.Empty)
                coVerify { dataSource.getMenus() }
            }
        }
    }

    @Test
    fun createOrder_success() {
        val mockCart =
            Cart(
                id = 1,
                menuName = "Menu 1",
                itemQuantity = 2,
                itemNotes = "Spicy",
                menuPrice = 20000.0,
                menuImgUrl = "img",
            )

        coEvery { dataSource.createOrder(any()) } returns CheckoutResponse(200, "Success", true)

        runTest {
            repository.createOrder("username", listOf(mockCart), 20000)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2201)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { dataSource.createOrder(any()) }
                }
        }
    }

    @Test
    fun createOrder_loading() {
        val mockCart =
            Cart(
                id = 1,
                menuName = "Menu 1",
                itemQuantity = 2,
                itemNotes = "Spicy",
                menuPrice = 20000.0,
                menuImgUrl = "img",
            )

        coEvery { dataSource.createOrder(any()) } coAnswers {
            delay(2000)
            CheckoutResponse(200, "Success", true)
        }

        runTest {
            repository.createOrder("username", listOf(mockCart), 20000)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(110)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                    coVerify { dataSource.createOrder(any()) }
                }
        }
    }

//    @Test
//    fun createOrder_error() {
//    }
}
