package com.example.foodiesapp.data.source.network.model.category

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
)
