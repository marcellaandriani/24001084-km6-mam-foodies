package com.example.foodiesapp.data.source.network.service

import com.example.foodiesapp.BuildConfig
import com.example.foodiesapp.data.source.network.model.category.CategoryResponse
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutRequestPayload
import com.example.foodiesapp.data.source.network.model.checkout.CheckoutResponse
import com.example.foodiesapp.data.source.network.model.menu.MenuResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FoodiesApiService {

    @GET("category")
    suspend fun getCategories() : CategoryResponse

    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenuResponse

    @POST("order")
    suspend fun createOrder(@Body payload : CheckoutRequestPayload): CheckoutResponse

    companion object {
        @JvmStatic
        operator fun invoke(): FoodiesApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(FoodiesApiService::class.java)
        }
    }
}