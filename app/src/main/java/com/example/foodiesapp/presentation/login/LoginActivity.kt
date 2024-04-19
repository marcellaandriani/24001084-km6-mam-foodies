package com.example.foodiesapp.presentation.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.foodiesapp.R
import com.example.foodiesapp.data.datasource.auth.AuthDataSource
import com.example.foodiesapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.data.repository.UserRepositoryImpl
import com.example.foodiesapp.data.source.network.firebase.FirebaseService
import com.example.foodiesapp.data.source.network.firebase.FirebaseServiceImpl
import com.example.foodiesapp.databinding.ActivityLoginBinding
import com.example.foodiesapp.presentation.main.MainActivity
import com.example.foodiesapp.presentation.register.RegisterActivity
import com.example.foodiesapp.presentation.register.RegisterViewModel
import com.example.foodiesapp.utils.GenericViewModelFactory
import com.example.foodiesapp.utils.highLightWord
import com.example.foodiesapp.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels {
        val service: FirebaseService = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(LoginViewModel(repository))
    }

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
        viewModel.doLogin(email, password).observe(this){result ->
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