package com.example.doa_app.presentation.ui.activity.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.doa_app.R
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.User
import com.example.doa_app.databinding.FragmentUserProfileBinding
import com.example.doa_app.presentation.ui.activity.LoginActivity
import com.example.doa_app.presentation.view_model.UserProfileViewModel
import com.example.doa_app.utils.gson
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val userProfileViewModel: UserProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userProfileViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Glide.with(requireContext())
                    .load(user.photo)
                    .into(binding.noprofileimage)
                binding.emailuser.text = user.email
            }
        }

        binding.exitButton.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
