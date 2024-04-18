package com.example.foodiesapp.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodiesapp.data.repository.CartRepository
import com.example.foodiesapp.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAllCart().collect { result ->
            }
        }
    }

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}
