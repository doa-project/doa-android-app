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
import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.data.repository.PublicationRepositoryImpl
import com.example.doa_app.databinding.FragmentListPublicationBinding
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.adapter.ListPublicationsAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnTop
import com.example.doa_app.utils.TreatmentApiObjects
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeoutException

class ListPublicationFragment : Fragment(R.layout.fragment_list_publication)
//    , OnPublicationClickListener
{
    private var _binding: FragmentListPublicationBinding? = null
    private val binding get() = _binding!!
    private var listPublicationAdapter: ListPublicationsAdapter? = null
    private lateinit var publicationMobList: MutableList<PublicationMob>
    private val publicationUseCase = PublicationUseCase(PublicationRepositoryImpl(RetrofitInstance.service))
    private val treatmentApiObjects = TreatmentApiObjects()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPublicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun hideLoading() {
        _binding?.progressBar3?.visibility = View.INVISIBLE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            var response: List<PublicationAPI>?
            try {
                publicationUseCase.getAllPublications().let {
                    response = it.body()
                    if (response.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), "No publications found", Toast.LENGTH_SHORT).show()
                        Log.e(ContentValues.TAG, "No publications found")
                        hideLoading()
                    } else {
                        Log.d(ContentValues.TAG, "Response: $response")
                        publicationMobList = treatmentApiObjects.publicationApiToPublicationList(response!!)
                        setupRecyclerView()
                        hideLoading()
                    }
                }
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
//    override fun onPublicationClick(publication: Publication) {
//        val intent = Intent(activity, InstitutionProfileFragment::class.java)
//        intent.putExtra("INSTITUTION_ID", publication.institutionId)
//        startActivity(intent)
//    }

    private fun setupRecyclerView() = binding.publicationRecycleViewPublication.apply {
        listPublicationAdapter = ListPublicationsAdapter()
        listPublicationAdapter?.publicationMobList = publicationMobList
        adapter = listPublicationAdapter
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(SpacingOnTop(16))
    }
    private fun showLoading() {
        binding.progressBar3.visibility = View.VISIBLE
    }
}