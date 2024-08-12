package com.example.doa_app.presentation.ui.activity.fragments

import android.content.ContentValues
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
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.databinding.FragmentListCampaignBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.presentation.ui.view.adapter.ListCampaignAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.utils.TreatmentApiObjects
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListCampaignFragment : Fragment(R.layout.fragment_list_campaign)
//    , OnCampaignClickListener
{
    private var _binding: FragmentListCampaignBinding? = null
    private val binding get() = _binding!!
    private var listCampaignAdapter: ListCampaignAdapter? = null
    private lateinit var campaignMobList: MutableList<CampaignMob>
    private val campaignUseCase = CampaignUseCase(CampaignRepositoryImpl(RetrofitInstance.service))
    private val treatmentApiObjects = TreatmentApiObjects()
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

    private fun hideLoading() {
        _binding?.progressBar2?.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        showLoading()
        var response: List<CampaignAPI>?
        lifecycleScope.launch {
            try {
                campaignUseCase.getAllCampaign().let {
                    response = it.body()
                    if (response.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "No campaigns found!", Toast.LENGTH_SHORT).show()
                        Log.e(ContentValues.TAG, "No publications found")
                        hideLoading()
                    } else {
                        Log.d(ContentValues.TAG, "Response: $response")
                        campaignMobList = treatmentApiObjects.campaignApiToCampaignList(response!!)
                        setupRecyclerView()
                        hideLoading()
                    }
                }
                hideLoading()
            } catch (e: IOException) {
                Log.e(ContentValues.TAG, "IOException, you might not have internet connection: ${e.message}")
                Toast.makeText(requireContext(), "You might not have internet connection", Toast.LENGTH_SHORT).show()
                hideLoading()
                return@launch
            } catch (e: TimeoutException) {
                Log.e(ContentValues.TAG, "TimeoutException, request timed out: ${e.message}")
                Toast.makeText(requireContext(), "Request timed out", Toast.LENGTH_SHORT).show()
                hideLoading()
                return@launch
            } catch (e: Exception) {
                Log.e(ContentValues.TAG, "Exception, an error occurred: ${e.message}")
                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                hideLoading()
                return@launch
            }
        }
    }
    private fun setupRecyclerView() = binding.campaignRecycle.apply {
        listCampaignAdapter = ListCampaignAdapter()
        listCampaignAdapter!!.campaignsList = campaignMobList
        adapter = listCampaignAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
    }

//    override fun onCampaignClick(campaign: Campaign) {
//        val intent = Intent(activity, InstitutionProfileFragment::class.java)
//        intent.putExtra("INSTITUTION_ID", campaign.institutionId)
//        startActivity(intent)
//    }
    private fun showLoading() {
        binding.progressBar2.visibility = View.VISIBLE
    }
}