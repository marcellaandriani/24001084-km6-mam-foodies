package com.example.foodiesapp.presentation.detailfood

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.repository.CartRepository
import com.example.foodiesapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import java.lang.IllegalStateException

class DetailFoodViewModel(
    private val extras: Bundle?,
    private val cartRepository: CartRepository,
) : ViewModel() {
    val menu = extras?.getParcelable<Menu>(DetailFoodActivity.EXTRA_MENU)

    val menuCountLiveData =
        MutableLiveData(0).apply {
            postValue(0)
        }

    val priceLiveData =
        MutableLiveData<Double>().apply {
            postValue(0.0)
        }

    fun add() {
        val count = (menuCountLiveData.value ?: 0) + 1
        menuCountLiveData.postValue(count)
        priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
    }

    fun minus() {
        if ((menuCountLiveData.value ?: 0) > 0) {
            val count = (menuCountLiveData.value ?: 0) - 1
            menuCountLiveData.postValue(count)
            priceLiveData.postValue(menu?.price?.times(count) ?: 0.0)
        }
    }

    fun addToCart(): LiveData<ResultWrapper<Boolean>> {
        return menu?.let {
            val quantity = menuCountLiveData.value ?: 0
            cartRepository.createCart(it, quantity).asLiveData(Dispatchers.IO)
        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Menu not found"))) }
    }
}
