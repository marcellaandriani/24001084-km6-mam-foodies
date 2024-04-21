package com.example.foodiesapp.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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
        observeProfileData()
        observeEditMode()
    }

    private fun observeProfileData() {
        viewModel.getCurrentUser()?.let { user ->
            binding.etUsername.setText(user.username)
            binding.etEmail.setText(user.email)
        }
        viewModel.profileData.observe(viewLifecycleOwner) { profile ->
            profile?.let {
                binding.profileImageView.load(it.profileImg) {
                    crossfade(true)
                    error(R.drawable.ic_tab_profile)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }


    private fun setClickListener() {
        binding.ivEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
            binding.btnChangeProfile.setOnClickListener {
                if (checkNameValidation()) {
                    changeProfileData()
                }
            }
        binding.btnLogout.setOnClickListener{
            viewModel.doLogout()
            navigateToLogin()
            val navController = findNavController()
            navController.navigate(R.id.menu_tab_home)
        }
        binding.tvChangePwd.setOnClickListener {
            requestChangePassword()
            viewModel.doChangePasswordByEmail()
        }

    }

    private fun requestChangePassword() {
        val dialog = AlertDialog.Builder(requireContext())
            .setMessage("Change password request sent to your email Please check to your inbox or spam")
            .setPositiveButton(
                "Okay"
            ) { dialog, id ->
            }.create()
        dialog.show()
    }

    private fun checkNameValidation(): Boolean {
        val username = binding.etUsername.text.toString().trim()
        return if (username.isEmpty()) {
            binding.tilUsername.isErrorEnabled = true
            binding.tilUsername.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.tilUsername.isErrorEnabled = false
            true
        }
    }

    private fun changeProfileData() {
        val username = binding.etUsername.text.toString().trim()
        viewModel.updateUsername(username)

    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner){ isEditMode ->
            isEditMode?.let {
                binding.etUsername.isEnabled = it
                binding.etEmail.isEnabled = it
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
