package com.example.foodiesapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.foodiesapp.R
import com.example.foodiesapp.data.datasource.auth.AuthDataSource
import com.example.foodiesapp.data.datasource.auth.FirebaseAuthDataSource
import com.example.foodiesapp.data.repository.UserRepository
import com.example.foodiesapp.data.repository.UserRepositoryImpl
import com.example.foodiesapp.data.source.network.firebase.FirebaseService
import com.example.foodiesapp.data.source.network.firebase.FirebaseServiceImpl
import com.example.foodiesapp.databinding.FragmentProfileBinding
import com.example.foodiesapp.presentation.login.LoginActivity
import com.example.foodiesapp.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels{
        val service: FirebaseService = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginStatus()
        setClickListener()
        observeEditMode()
        observeProfileData()
    }

    private fun observeProfileData() {
        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.profileImageView.load(it.profileImg) {
                    crossfade(true)
                    error(R.drawable.ic_tab_profile)
                    transformations(CircleCropTransformation())
                }
                binding.etUsername.setText(it.username)
                binding.etPassword.setText(it.password)
                binding.etEmail.setText(it.email)
                binding.etTelephone.setText(it.telephone)
            }
        }
    }

    private fun setClickListener() {
        binding.ivEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.btnLogout.setOnClickListener{
            viewModel.doLogout()
            navigateToLogin()
            val navController = findNavController()
            navController.navigate(R.id.menu_tab_home)
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner){ isEditMode ->
            isEditMode?.let {
                binding.etUsername.isEnabled = it
                binding.etPassword.isEnabled = it
                binding.etEmail.isEnabled = it
                binding.etTelephone.isEnabled = it
            }
        }
    }

    private fun observeLoginStatus() {
        if (!viewModel.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
