package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.repository.PublicationRepositoryImpl
import com.example.doa_app.databinding.FragmentListPublicationBinding
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.adapter.ListPublicationsAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnTop
import com.example.doa_app.presentation.view_model.ListPublicationViewModel
import com.example.doa_app.presentation.view_model.factory.ListPublicationViewModelFactory
import com.example.doa_app.utils.SharedPreferences
import com.example.doa_app.utils.TreatmentApiObjects

class ListPublicationFragment : Fragment(R.layout.fragment_list_publication) {
    private var _binding: FragmentListPublicationBinding? = null
    private val sharedPreferences = SharedPreferences(requireContext(), "SharedPreferences")
    private val binding get() = _binding!!
    private val listPublicationViewModel: ListPublicationViewModel by viewModels{
        ListPublicationViewModelFactory(
            PublicationUseCase(PublicationRepositoryImpl(RetrofitInstance.service)),
            TreatmentApiObjects(),
            sharedPreferences
        )
    }
    private var listPublicationAdapter: ListPublicationsAdapter? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        listPublicationViewModel.loadingVisibility.observe(viewLifecycleOwner) {
            binding.progressBar3.visibility = it
        }

        listPublicationViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        listPublicationViewModel.publicationMobList.observe(viewLifecycleOwner) { publications ->
            listPublicationAdapter?.publicationMobList = publications.toMutableList()
            listPublicationAdapter?.notifyDataSetChanged()
        }

        listPublicationViewModel.fetchPublications()
    }

    private fun setupRecyclerView() = binding.publicationRecycleViewPublication.apply {
        listPublicationAdapter = ListPublicationsAdapter()
        adapter = listPublicationAdapter
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        addItemDecoration(SpacingOnTop(16))
    }
}
