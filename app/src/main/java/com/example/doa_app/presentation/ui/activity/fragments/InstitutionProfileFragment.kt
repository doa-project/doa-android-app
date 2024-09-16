package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doa_app.R
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.databinding.FragmentInstitutionProfileBinding
import com.example.doa_app.utils.ImageUtils
import com.google.gson.Gson

class InstitutionProfileFragment : Fragment(R.layout.fragment_institution_profile) {
    private var _binding: FragmentInstitutionProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var institutionLogged: Institution
    private val utils = ImageUtils()
//    private val institutionUseCase = InstitutionUseCase(InstitutionRepositoryImpl(RetrofitInstance.service))
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstitutionProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val data = it.getString("loggedUser")
            val dataJson = Gson().fromJson(data, Institution::class.java)
            institutionLogged = dataJson

        }
//        binding.profileimage.setImageBitmap()
        binding.institutionname.text = institutionLogged.name
        binding.descripition.text = institutionLogged.description

    }
}