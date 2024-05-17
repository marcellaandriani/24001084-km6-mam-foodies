package com.example.foodiesapp.data.datasource.auth

import com.example.foodiesapp.data.source.network.firebase.FirebaseService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService

    private lateinit var dataSource: FirebaseAuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        val email = "test@example.com"
        val password = "password123"
        coEvery { service.doLogin(email, password) } returns true
        val result = runBlocking { dataSource.doLogin(email, password) }
        assertTrue(result)
    }

    @Test
    fun doRegister() {
        val username = "testUser"
        val email = "test@example.com"
        val password = "password123"
        val numberPhone = "123456789"
        coEvery { service.doRegister(username, email, password, numberPhone) } returns true
        val result = runBlocking { dataSource.doRegister(username, email, password, numberPhone) }
        assertTrue(result)
    }

    @Test
    fun updateProfile() {
        val username = "newUsername"
        coEvery { service.updateProfile(username) } returns true
        val result = runBlocking { dataSource.updateProfile(username) }
        assertTrue(result)
    }

    @Test
    fun updatePassword() {
        val newPassword = "newPassword123"
        coEvery { service.updatePassword(newPassword) } returns true
        val result = runBlocking { dataSource.updatePassword(newPassword) }
        assertTrue(result)
    }

    @Test
    fun updateEmail() {
        val newEmail = "new@example.com"
        coEvery { service.updateEmail(newEmail) } returns true
        val result = runBlocking { dataSource.updateEmail(newEmail) }
        assertTrue(result)
    }

    @Test
    fun requestChangePasswordByEmail() {
        every { service.requestChangePasswordByEmail() } returns true
        val result = dataSource.requestChangePasswordByEmail()
        assertTrue(result)
    }

    @Test
    fun doLogout() {
        every { service.doLogout() } returns true
        val result = dataSource.doLogout()
        assertTrue(result)
    }

    @Test
    fun isLoggedIn() {
        every { service.isLoggedIn() } returns true
        val result = dataSource.isLoggedIn()
        assertTrue(result)
    }

//    @Test
//    fun getCurrentUser() {
//    }
}
