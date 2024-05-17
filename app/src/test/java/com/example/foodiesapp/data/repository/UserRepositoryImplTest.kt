package com.example.foodiesapp.data.repository

import com.example.foodiesapp.data.datasource.auth.AuthDataSource
import com.example.foodiesapp.data.model.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var dataSource: AuthDataSource
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepositoryImpl(dataSource)
    }

//    @Test
//    fun doLogin() {
//    }
//
//    @Test
//    fun doRegister() {
//    }
//
//    @Test
//    fun updateProfile() {
//    }
//
//    @Test
//    fun updatePassword() {
//    }
//
//    @Test
//    fun updateEmail() {
//    }

    @Test
    fun requestChangePasswordByEmail() {
        val expectedResult = true
        every { dataSource.requestChangePasswordByEmail() } returns expectedResult
        val result = repository.requestChangePasswordByEmail()
        assert(result)
    }

    @Test
    fun doLogout() {
        val expectedResult = true
        every { dataSource.doLogout() } returns expectedResult
        val result = repository.doLogout()
        assert(result)
    }

    @Test
    fun isLoggedIn() {
        val expectedResult = true
        every { dataSource.isLoggedIn() } returns expectedResult
        val result = repository.isLoggedIn()
        assert(result)
    }

    @Test
    fun getCurrentUser() {
        val user = User("1", "username", "email@example.com")
        every { dataSource.getCurrentUser() } returns user
        val result = repository.getCurrentUser()
        assert(result == user)
    }
}
