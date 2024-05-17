package com.example.foodiesapp.presentation.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.foodiesapp.data.repository.CategoryRepository
import com.example.foodiesapp.data.repository.MenuRepository
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.data.source.local.pref.UserPreference
import com.example.foodiesapp.tools.MainCoroutineRule
import com.example.foodiesapp.tools.getOrAwaitValue
import com.example.foodiesapp.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
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

class HomeViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var categoryRepository: CategoryRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    @MockK
    lateinit var userPreference: UserPreference

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        every { userPreference.isUsingGridMode() } returns true
        every { userPreference.setUsingGridMode(any()) } just Runs

        viewModel =
            spyk(
                HomeViewModel(
                    categoryRepository,
                    menuRepository,
                    userPreference,
                    userRepository,
                ),
            )
    }

    @Test
    fun isUsingGrid() {
        val observer = mockk<Observer<Boolean>>(relaxed = true)
        viewModel.isUsingGrid.observeForever(observer)
        viewModel.changeGridMode()
        verify { observer.onChanged(!userPreference.isUsingGridMode()) }

        viewModel.isUsingGrid.removeObserver(observer)
    }

    @Test
    fun changeGridMode() {
        val initialValue = viewModel.isUsingGrid.value
        viewModel.changeGridMode()
        val finalValue = viewModel.isUsingGrid.value
        assertNotEquals(initialValue, finalValue)
    }

    @Test
    fun getMenus() {
        every { menuRepository.getMenus() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        every { menuRepository.getMenus(any()) } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getMenus().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { menuRepository.getMenus(any()) }
    }

    @Test
    fun getCategories() {
        every { categoryRepository.getCategories() } returns
            flow {
                emit(ResultWrapper.Success(listOf(mockk(), mockk())))
            }
        val result = viewModel.getCategories().getOrAwaitValue()
        assertEquals(2, result.payload?.size)
        verify { categoryRepository.getCategories() }
    }

    @Test
    fun getCurrentUser() {
    }
}
