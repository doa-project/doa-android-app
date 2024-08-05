package com.example.doa_app.presentation.ui.activity.fragments

import android.content.ContentValues
import android.content.Intent
import android.net.http.HttpException
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
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.repository.PublicationRepositoryImpl
import com.example.doa_app.databinding.FragmentListPublicationBinding
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.ListPublicationsAdapter
import com.example.doa_app.presentation.ui.view.OnPublicationClickListener
import com.example.doa_app.presentation.ui.view.SpacingOnSide
import java.io.IOException

class ListPublicationFragment : Fragment(R.layout.fragment_list_publication),
    OnPublicationClickListener {
    private var _binding: FragmentListPublicationBinding? = null
    private val binding get() = _binding!!
    private var listPublicationAdapter: ListPublicationsAdapter? = null
    private lateinit var publicationList: MutableList<Publication>
    private val publicationUseCase = PublicationUseCase(PublicationRepositoryImpl(RetrofitInstance.service))
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

        lifecycleScope.launchWhenCreated {
            val response = try {
                publicationUseCase.getAllPublications()
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
                publicationList = (response.body() as MutableList<Publication>?)!!
                setpRecyclerView()
            }
        }
    }
    override fun onPublicationClick(publication: Publication) {
        val intent = Intent(activity, InstitutionProfileFragment::class.java)
        intent.putExtra("INSTITUTION_ID", publication.institutionId)
        startActivity(intent)
    }

    private fun setpRecyclerView() = binding.publicationRecycleView.apply {
        listPublicationAdapter = ListPublicationsAdapter(this@ListPublicationFragment)
        listPublicationAdapter!!.publicationList = publicationList
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }
}