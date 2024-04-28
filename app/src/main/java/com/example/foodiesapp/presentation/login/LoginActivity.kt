package com.example.foodiesapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.foodiesapp.databinding.ActivityLoginBinding
import com.example.foodiesapp.presentation.main.MainActivity
import com.example.foodiesapp.presentation.register.RegisterActivity
import com.example.foodiesapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutFormLogin.btnLogin.setOnClickListener {
            inputLogin()
        }
        binding.layoutFormLogin.tvNavToRegister.setOnClickListener{
            navigateRegister()
        }
    }

    private fun inputLogin() {
        val email = binding.layoutFormLogin.etEmailLogin.text.toString().trim()
        val password = binding.layoutFormLogin.etPasswordLogin.text.toString().trim()
        doLogin(email, password)
    }

    private fun doLogin(email: String, password: String) {
        loginViewModel.doLogin(email, password).observe(this){ result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutFormLogin.pbLogin.isVisible = false
                    binding.layoutFormLogin.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.layoutFormLogin.pbLogin.isVisible = true
                    binding.layoutFormLogin.pbLogin.isEnabled = false
                },
                doOnError = {
                    binding.layoutFormLogin.pbLogin.isVisible = false
                    binding.layoutFormLogin.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}