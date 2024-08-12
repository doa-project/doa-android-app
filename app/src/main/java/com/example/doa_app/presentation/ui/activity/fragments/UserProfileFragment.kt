package com.example.doa_app.presentation.ui.activity.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.doa_app.R
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.User
import com.example.doa_app.databinding.FragmentUserProfileBinding
import com.example.doa_app.presentation.ui.activity.LoginActivity
import com.google.gson.Gson

class UserProfileFragment : Fragment(R.layout.fragment_user_profile) {
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val gson = Gson()
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

        arguments?.let {
            var data = it.getString("loggedUser")
            if (data != null) {
                val dataJson = gson.fromJson(data, User::class.java)
                binding.emailuser.text = dataJson.email
            }
            else {
                data = it.getString("loggedInstitution")
                val dataJson = gson.fromJson(data, Institution::class.java)
                binding.emailuser.text = dataJson.email
            }
        }

        binding.exitButton.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}