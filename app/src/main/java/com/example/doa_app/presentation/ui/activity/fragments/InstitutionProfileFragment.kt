package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.Institution
import com.example.doa_app.data.repository.InstitutionRepositoryImpl
import com.example.doa_app.databinding.FragmentInstitutionProfileBinding
import com.example.doa_app.domain.usecase.InstitutionUseCase
import com.example.doa_app.utils.ImageUtils
import com.google.gson.Gson
import kotlinx.coroutines.launch

class InstitutionProfileFragment : Fragment(R.layout.fragment_institution_profile) {
    private var _binding: FragmentInstitutionProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var institutionLogged: Institution
    private val utils = ImageUtils()
    private val institutionUseCase = InstitutionUseCase(InstitutionRepositoryImpl(RetrofitInstance.service))
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
            val data = it.getString("INSTITUTION_ID")
//           lifecycleScope.launch {
//               institutionUseCase.getInstitution(data!!).body()?.let {
//                       it1 -> institutionLogged = it1
//                   binding.profileimage.setImageBitmap(utils.base64ToBitmap(institutionLogged.photo.toString()))
//                   binding.institutionname.text = institutionLogged.name
//                   binding.descripition.text = institutionLogged.description
//               }
//            }
        }
        binding.profileimage.setImageBitmap(utils.base64ToBitmap(institutionLogged.photo.toString()))
        binding.institutionname.text = institutionLogged.name
        binding.descripition.text = institutionLogged.description


        binding.publicationButton.setImageResource(R.drawable.ipublicationss)
        binding.campaignButton.setImageResource(R.drawable.icampaignn)

        binding.publicationButton.setOnClickListener {
            binding.publicationButton.setImageResource(R.drawable.ipublicationss)
            binding.campaignButton.setImageResource(R.drawable.icampaignn)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentlist, ListPublicationFragment())
                .commit()
        }
        binding.campaignButton.setOnClickListener {
            binding.publicationButton.setImageResource(R.drawable.ipublicationsn)
            binding.campaignButton.setImageResource(R.drawable.icampaigns)

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentlist, ListCampaignFragment())
                .commit()
        }

    }
}