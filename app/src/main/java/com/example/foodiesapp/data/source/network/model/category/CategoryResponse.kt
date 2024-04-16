package com.example.foodiesapp.data.source.network.model.category


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryItemResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)