package com.example.doa_app.presentation.ui.activity.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.appModule
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Image
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.data.repository.PublicationRepositoryImpl

import com.example.doa_app.databinding.ActivityFragmentAddPublicationBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.ListImagesAdapter
import com.example.doa_app.presentation.ui.view.SpacingOnSide
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

private var typePublication = 0
private var listOfImages: List<Image> = mutableListOf()
private val publicationRepository = PublicationRepositoryImpl(RetrofitInstance.service)
private val publicationUseCase = PublicationUseCase(publicationRepository)
private val campaignRepository = CampaignRepositoryImpl(RetrofitInstance.service)
private val campaignUseCase = CampaignUseCase(campaignRepository)

private var listImageAdapter = ListImagesAdapter()
private var institutionId = ""
class AddPublicationFragment : Fragment(R.layout.activity_fragment_add_publication) {
    private var _binding: ActivityFragmentAddPublicationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityFragmentAddPublicationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val context = this.context
        super.onViewCreated(view, savedInstanceState)
        GlobalContext.startKoin {
            if (context != null) {
                androidContext(context)
            }
            modules(appModule)
        }

        binding.editTextDate.visibility = View.INVISIBLE
        binding.addressInputText.visibility = View.INVISIBLE

        val gridLayoutManager = GridLayoutManager(view.context, 1)

        gridLayoutManager.orientation =
            GridLayoutManager.HORIZONTAL

        binding.listImageRecycleView.layoutManager = gridLayoutManager

        binding.galeryButton.setOnClickListener {
            //Entrar na galeria

        }

        binding.campaignButton.setOnClickListener {
            binding.campaignButton.setBackgroundResource(R.drawable.button_simple)
            binding.publicationButton.setBackgroundResource(R.drawable.red_stroke)
            binding.editTextDate.visibility = View.VISIBLE
            binding.addressInputText.visibility = View.VISIBLE
            typePublication = 1

        }
        binding.publicationButton.setOnClickListener {
            binding.campaignButton.setBackgroundResource(R.drawable.red_stroke)
            binding.publicationButton.setBackgroundResource(R.drawable.button_simple)
            binding.editTextDate.visibility = View.INVISIBLE
            binding.addressInputText.visibility = View.INVISIBLE
            typePublication = 0
        }

        binding.publishButton.setOnClickListener {

            if (typePublication == 0) {
                val description = binding.editTextTextMultiLine.text.toString()
                val address = binding.addressInputText.text.toString()
                val date = binding.editTextDate.text.toString()

                if (description.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty()) {
                    val campaign = Campaign(null, institutionId, null, null, description, listOfImages, date, address)
                    lifecycleScope.launch {
                        campaignUseCase.createCampaign(campaign)
                    }
                    binding.editTextTextMultiLine.setText("")
                    binding.addressInputText.setText("")
                    binding.editTextDate.setText("")
                }else{
                    binding.editTextTextMultiLine.error = "Preencha todos os campos"
                    binding.addressInputText.error = "Preencha todos os campos"
                    binding.editTextDate.error = "Preencha todos os campos"
                }

            } else {
                val description = binding.editTextTextMultiLine.text.toString()

                if (description.isNotEmpty()) {
                    val publication = Publication(null, institutionId, null, null, description, listOfImages)
                    lifecycleScope.launch {
                        publicationUseCase.createPublication(publication)
                    }
                    binding.editTextTextMultiLine.setText("")
                }else{
                    binding.editTextTextMultiLine.error = "Preencha todos os campos"
                }
            }
        }
    }
    private fun setupRecyclerView() = binding.listImageRecycleView.apply {
        listImageAdapter = ListImagesAdapter()
        adapter = listImageAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }
}