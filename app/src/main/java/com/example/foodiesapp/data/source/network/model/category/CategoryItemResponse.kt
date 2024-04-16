package com.example.foodiesapp.data.source.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?
)