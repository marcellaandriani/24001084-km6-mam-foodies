package com.example.foodiesapp.data.source.network.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MenuItemResponse(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val nama: String?,
    @SerializedName("harga_format")
    val hargaFormat: String?,
    @SerializedName("harga")
    val harga: Int?,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("alamat_resto")
    val alamatResto: String?,
)
