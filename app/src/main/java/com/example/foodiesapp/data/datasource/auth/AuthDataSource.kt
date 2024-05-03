package com.example.foodiesapp.data.datasource.auth

import com.example.foodiesapp.data.model.User
import com.example.foodiesapp.data.model.toUser
import com.example.foodiesapp.data.source.network.firebase.FirebaseService
import java.lang.Exception

interface AuthDataSource {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        username: String,
        email: String,
        password: String,
        numberPhone: String,
    ): Boolean

    suspend fun updateProfile(username: String? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): User?
}

class FirebaseAuthDataSource(private val service: FirebaseService) : AuthDataSource {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean {
        return service.doLogin(email, password)
    }

    override suspend fun doRegister(
        username: String,
        email: String,
        password: String,
        numberPhone: String,
    ): Boolean {
        return service.doRegister(
            username = username,
            numberPhone = numberPhone,
            email = email,
            password = password,
        )
    }

    override suspend fun updateProfile(username: String?): Boolean {
        return service.updateProfile(username)
    }

    override suspend fun updatePassword(newPassword: String): Boolean {
        return service.updatePassword(newPassword)
    }

    override suspend fun updateEmail(newEmail: String): Boolean {
        return service.updateEmail(newEmail)
    }

    override fun requestChangePasswordByEmail(): Boolean {
        return service.requestChangePasswordByEmail()
    }

    override fun doLogout(): Boolean {
        return service.doLogout()
    }

    override fun isLoggedIn(): Boolean {
        return service.isLoggedIn()
    }

    override fun getCurrentUser(): User? {
        return service.getCurrentUser().toUser()
    }
}
