package com.example.foodiesapp.data.datasource.user

import com.example.foodiesapp.data.source.local.pref.UserPreference

interface UserDataSource {
    suspend fun isUsingGridMode(): Boolean
    suspend fun setUsingGridMode(isUsingGridMode: Boolean)
}

class UserDataSourceImpl(private val userPreference: UserPreference) : UserDataSource {

    override suspend fun isUsingGridMode(): Boolean {
        return userPreference.isUsingGridMode()
    }

    override suspend fun setUsingGridMode(isUsingGridMode: Boolean) {
        userPreference.setUsingGridMode(isUsingGridMode)
    }
}
