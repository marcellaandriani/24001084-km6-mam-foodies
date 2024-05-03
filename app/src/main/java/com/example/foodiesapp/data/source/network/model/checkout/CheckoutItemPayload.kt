package com.example.foodiesapp.data.source.network.model.checkout

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CheckoutItemPayload(
    @SerializedName("nama")
    val nama: String? = null,
    @SerializedName("qty")
    val qty: Int? = null,
    @SerializedName("catatan")
    val catatan: String? = null,
    @SerializedName("harga")
    val harga: Int,
)
