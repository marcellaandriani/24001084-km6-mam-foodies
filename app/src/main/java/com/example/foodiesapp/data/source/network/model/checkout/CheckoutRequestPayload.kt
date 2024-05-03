package com.example.foodiesapp.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutRequestPayload(
    @SerializedName("total")
    val total: Int? = null,
    @SerializedName("username")
    val username: String? = null,
    @SerializedName("orders")
    val orders: List<CheckoutItemPayload>,
)
