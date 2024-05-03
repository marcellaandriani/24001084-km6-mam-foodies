package com.example.foodiesapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu(
    var id: String? = UUID.randomUUID().toString(),
    var name: String,
    var imgUrl: String,
    val mapsUrl: String,
    var price: Double,
    var formatPrice: String,
    var description: String,
    var address: String,
) : Parcelable
