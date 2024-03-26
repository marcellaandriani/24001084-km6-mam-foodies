package com.example.foodiesapp.presentation.detailfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodiesapp.R
import com.example.foodiesapp.databinding.ActivityCheckoutBinding
import com.example.foodiesapp.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity() {

    private val binding :  ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}