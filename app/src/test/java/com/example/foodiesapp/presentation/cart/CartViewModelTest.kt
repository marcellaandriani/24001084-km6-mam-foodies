package com.example.foodiesapp.presentation.cart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.foodiesapp.data.repository.CartRepository
import com.example.foodiesapp.tools.MainCoroutineRule
import com.example.foodiesapp.tools.getOrAwaitValue
import com.example.foodiesapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CartViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: CartRepository

    lateinit var viewModel: CartViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(CartViewModel(repo))
    }

    @Test
    fun getAllCarts_success() {
        every { repo.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Pair(
                            listOf(
                                mockk(relaxed = true),
                                mockk(relaxed = true),
                            ),
                            8000.0,
                        ),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun getAllCarts_loading() {
        every { repo.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Loading(
                        Pair(
                            listOf(
                                mockk(relaxed = true),
                                mockk(relaxed = true),
                            ),
                            8000.0,
                        ),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun getAllCarts_empty() {
        every { repo.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Empty(
                        Pair(
                            listOf(
                                mockk(relaxed = true),
                                mockk(relaxed = true),
                            ),
                            8000.0,
                        ),
                    ),
                )
            }
        val result = viewModel.getAllCarts().getOrAwaitValue()
        assertEquals(2, result.payload?.first?.size)
        assertEquals(8000.0, result.payload?.second)
    }

    @Test
    fun getAllCarts_error() {
        val errorMessage = "Failed to get user cart data"
        every { repo.getUserCartData() } returns
            flow {
                emit(
                    ResultWrapper.Error(
                        RuntimeException(errorMessage),
                        Pair(
                            emptyList(),
                            0.0,
                        ),
                    ),
                )
            }

        val result = viewModel.getAllCarts().getOrAwaitValue()

        assertTrue(result is ResultWrapper.Error)
        assertEquals(errorMessage, (result as ResultWrapper.Error).exception?.message)
        assertEquals(0, result.payload?.first?.size)
        assertEquals(0.0, result.payload?.second)
    }

    @Test
    fun decreaseCart() {
        every { repo.decreaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.decreaseCart(mockk())
        verify { repo.decreaseCart(any()) }
    }

    @Test
    fun increaseCart() {
        every { repo.increaseCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.increaseCart(mockk())
        verify { repo.increaseCart(any()) }
    }

    @Test
    fun removeCart() {
        every { repo.deleteCart(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.removeCart(mockk())
        verify { repo.deleteCart(any()) }
    }

    @Test
    fun setCartNotes() {
        every { repo.setCartNotes(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.setCartNotes(mockk())
        verify { repo.setCartNotes(any()) }
    }
}
