package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doa_app.R
import com.example.doa_app.presentation.view_model.ListCampaignOfInstitutionViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ListInstitutionCampaignFragment : Fragment() {

    private val listCampaignOfInstitutionViewModel = getViewModel<ListCampaignOfInstitutionViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_institution_campaign, container, false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}