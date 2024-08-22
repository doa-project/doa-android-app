package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.databinding.FragmentListCampaignBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.presentation.ui.view.adapter.ListCampaignAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.presentation.view_model.ListCampaignViewModel
import com.example.doa_app.presentation.view_model.factory.ListCampaignViewModelFactory
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.TreatmentApiObjects

class ListCampaignFragment : Fragment(R.layout.fragment_list_campaign) {

    private var _binding: FragmentListCampaignBinding? = null
    private val sharedPreferences = SharedPreferences(requireContext(), "SharedPreferences")
    private val binding get() = _binding!!
    private var listCampaignAdapter: ListCampaignAdapter? = null

    private val viewModel: ListCampaignViewModel by viewModels {
        ListCampaignViewModelFactory(
            CampaignUseCase(CampaignRepositoryImpl(RetrofitInstance.service)),
            TreatmentApiObjects(),
            sharedPreferences
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListCampaignBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.campaignMobList.observe(viewLifecycleOwner) {
            if (it != null) {
                setupRecyclerView(it)
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.getAllCampaigns()
    }

    private fun setupRecyclerView(campaignMobList: List<CampaignMob>) = binding.campaignRecycle.apply {
        listCampaignAdapter = ListCampaignAdapter()
        listCampaignAdapter!!.campaignsList = campaignMobList.toMutableList()
        adapter = listCampaignAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showLoading() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar2.visibility = View.INVISIBLE
    }
}
