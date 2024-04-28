package com.example.foodiesapp.presentation.checkout

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.foodiesapp.R
import com.example.foodiesapp.databinding.ActivityCheckoutBinding
import com.example.foodiesapp.presentation.checkout.adapter.PriceListAdapter
import com.example.foodiesapp.presentation.common.adapter.CartListAdapter
import com.example.foodiesapp.presentation.login.LoginActivity
import com.example.foodiesapp.utils.proceedWhen
import com.example.foodiesapp.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }


    private val checkoutViewModel: CheckoutViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCheckout.setOnClickListener {
            if (checkoutViewModel.isLoggedIn()) {
                checkoutViewModel.checkoutCart().observe(this) {
                    it.proceedWhen(
                        doOnSuccess = {
                            showSuccessDialog()
                        },
                        doOnLoading = {
                            binding.layoutState.root.isVisible = true
                            binding.layoutState.pbLoading.isVisible = true
                            binding.layoutState.tvError.isVisible = false
                            binding.layoutContent.root.isVisible = false
                            binding.layoutContent.rvCart.isVisible = false

                        },
                        doOnError = {
                            Toast.makeText(
                                this,
                                getString(R.string.error_checkout),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            } else {
                navigateToLogin()
            }
        }
    }

    private fun showSuccessDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_succes)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val ivSuccess = dialog.findViewById<ImageView>(R.id.iv_success)
        val closeBtn = dialog.findViewById<Button>(R.id.closeBtn)

        ivSuccess.setImageResource(R.drawable.ic_payment_success)
        closeBtn.setOnClickListener {
            dialog.dismiss()
            checkoutViewModel.deleteAllCart()
            onBackPressed()
            finish()
        }

        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        checkoutViewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = { data ->
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    binding.cvSectionOrder.isVisible = true
                    data.payload?.let { (carts, priceItems, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                        priceItemAdapter.submitData(priceItems)
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                },
                doOnError = { error ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = error.message.orEmpty()
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                },
                doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }
            )
        }
    }
}
