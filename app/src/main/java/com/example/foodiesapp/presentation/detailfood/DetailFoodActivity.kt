package com.example.foodiesapp.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.foodiesapp.R
import com.example.foodiesapp.data.datasource.cart.CartDataSource
import com.example.foodiesapp.data.datasource.cart.CartDatabaseDataSource
import com.example.foodiesapp.data.model.Menu
import com.example.foodiesapp.data.repository.CartRepository
import com.example.foodiesapp.data.repository.CartRepositoryImpl
import com.example.foodiesapp.data.source.local.database.AppDatabase
import com.example.foodiesapp.databinding.ActivityDetailFoodBinding
import com.example.foodiesapp.utils.GenericViewModelFactory
import com.example.foodiesapp.utils.proceedWhen
import com.example.foodiesapp.utils.toIndonesianFormat

    class DetailFoodActivity : AppCompatActivity() {
        private val binding: ActivityDetailFoodBinding by lazy {
            ActivityDetailFoodBinding.inflate(layoutInflater) }
        private val viewModel: DetailFoodViewModel by viewModels {
            val db = AppDatabase.getInstance(this)
            val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
            val rp: CartRepository = CartRepositoryImpl(ds)
            GenericViewModelFactory.create(
                DetailFoodViewModel(intent?.extras, rp)
            )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val menu = intent.getParcelableExtra<Menu>(EXTRA_MENU)
        menu?.let { bindMenu(it) }
        setClickListener()
        observeData()
    }

    private fun setClickListener() {
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.add()
        }
        binding.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this,
                        getString(R.string.text_add_to_cart_failed), Toast.LENGTH_SHORT).show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.text_loading) ,Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindMenu(menu: Menu) {
        with(binding) {
            ivDetailMenu.load(menu.imgUrl) {
                crossfade(true)
            }
            tvNameMenu.text = menu.name
            tvDescMenu.text = menu.description
            tvPriceMenu.text = menu.price.toIndonesianFormat()
            tvAddress.text = menu.address
            tvAddress.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(menu.mapsUrl)
                startActivity(i)
            }
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) { price ->
            binding.btnAddToCart.isEnabled = price != 0.0
            binding.btnAddToCart.text =
                getString(R.string.text_tambahkan_ke_keranjang, price.toIndonesianFormat())
        }
        viewModel.menuCountLiveData.observe(this) { count ->
            binding.tvTotal.text = count.toString()
        }
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}