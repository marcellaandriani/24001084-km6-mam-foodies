package com.example.foodiesapp.presentation.detailfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
    }

    private lateinit var binding: ActivityDetailFoodBinding
    private var quantity = 0
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val menu = intent.getParcelableExtra<Menu>(EXTRA_MENU)
        menu?.let { displayMenuDetail(it) }
    }

    private fun displayMenuDetail(menu: Menu) {
        binding.apply {
            ivDetailMenu.load(menu.imgUrl) {
                crossfade(true)
            }
            binding.tvNameMenu.text = menu.name
            binding.tvPriceMenu.text = menu.price.toString()
            binding.tvDescMenu.text = menu.description
            binding.tvAddress.text = menu.address
            quantity = 1
            tvTotal.text = quantity.toString()
            totalPrice = quantity * menu.unitPrice
            btnAddToCart.text = "Tambah ke Keranjang - Rp $totalPrice"

            ivRemove.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    tvTotal.text = quantity.toString()
                    totalPrice = quantity * menu.unitPrice
                    btnAddToCart.text = "Tambah ke Keranjang - Rp $totalPrice"
                } else {
                    Toast.makeText(
                        this@DetailFoodActivity,
                        "Maaf, minimal pembelian 1",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            ivPlus.setOnClickListener {
                quantity++
                tvTotal.text = quantity.toString()
                totalPrice = quantity * menu.unitPrice
                btnAddToCart.text = "Tambah ke Keranjang - Rp $totalPrice"
            }

            tvAddress.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(menu.mapsUrl))
                startActivity(i)
            }
        }
    }
}
