package com.example.doa_app.presentation.ui.activity.fragments

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.databinding.FragmentListCampaignBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.presentation.ui.view.ListCampaignAdapter
import com.example.doa_app.presentation.ui.view.OnCampaignClickListener
import com.example.doa_app.presentation.ui.view.SpacingOnSide
import retrofit2.HttpException
import java.io.IOException

class ListCampaignFragment : Fragment(R.layout.fragment_list_campaign), OnCampaignClickListener {
    private var _binding: FragmentListCampaignBinding? = null
    private val binding get() = _binding!!
    private var listCampaignAdapter: ListCampaignAdapter? = null
    private lateinit var campaignList: MutableList<Campaign>
    private val campaignUseCase = CampaignUseCase(CampaignRepositoryImpl(RetrofitInstance.service))
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

        lifecycleScope.launchWhenCreated {
            val response = try {
                campaignUseCase.getAllCampaign()
            } catch(e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection")
                Toast.makeText(requireContext(), "You might not have internet connection", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            } catch(e: HttpException) {
                Log.e(ContentValues.TAG, "HttpException, unexpected response")
                Toast.makeText(requireContext(), "Unexpected response", Toast.LENGTH_SHORT).show()
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                campaignList = (response.body() as MutableList<Campaign>?)!!
                setupRecyclerView()
            } else {
                Log.e(ContentValues.TAG, "Response not successful")
                Toast.makeText(requireContext(), "Response not successful", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupRecyclerView() = binding.campaignRecycle.apply {
        listCampaignAdapter = ListCampaignAdapter(this@ListCampaignFragment)
        listCampaignAdapter!!.campaignsList = campaignList
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onCampaignClick(campaign: Campaign) {
        val intent = Intent(activity, InstitutionProfileFragment::class.java)
        intent.putExtra("INSTITUTION_ID", campaign.institutionId)
        startActivity(intent)
    }
}