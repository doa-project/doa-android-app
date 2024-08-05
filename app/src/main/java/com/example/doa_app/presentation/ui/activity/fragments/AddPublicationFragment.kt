package com.example.doa_app.presentation.ui.activity.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.RetrofitInstance
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.data.model.Image
import com.example.doa_app.data.model.Publication
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.data.repository.PublicationRepositoryImpl
import com.example.doa_app.databinding.FragmentAddPublicationBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.ListImagesAdapter
import com.example.doa_app.presentation.ui.view.SpacingOnSide
import com.example.doa_app.utils.ImageUtils
import kotlinx.coroutines.launch

class AddPublicationFragment : Fragment(R.layout.fragment_add_publication) {
    private var _binding: FragmentAddPublicationBinding? = null
    private val binding get() = _binding!!

    private var typePublication = 0

    private var institutionId = ""

    private var listImageAdapter: ListImagesAdapter? = null
    private var campaignUseCase = CampaignUseCase(CampaignRepositoryImpl(RetrofitInstance.service))
    private var publicationUseCase = PublicationUseCase(PublicationRepositoryImpl(RetrofitInstance.service))
    private val imageUtils = ImageUtils()
    private val selectedImages = mutableListOf<Image>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPublicationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val redPrincipal = ContextCompat.getColor(requireContext(), R.color.red_principal)
        val white = ContextCompat.getColor(requireContext(), R.color.white)

        binding.editTextDate.visibility = View.INVISIBLE
        binding.addressInputText.visibility = View.INVISIBLE

        val gridLayoutManager = GridLayoutManager(view.context, 1)
        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
        binding.listImageRecycleView.layoutManager = gridLayoutManager

        binding.galeryButton.setOnClickListener {
            selectedImages.clear()
            openGallery()
        }

        binding.campaignButton.setOnClickListener {
            binding.campaignButton.setBackgroundResource(R.drawable.button_simple)
            binding.campaignButton.setTextColor(white)
            binding.publicationButton.setBackgroundResource(R.drawable.red_stroke_bt)
            binding.publicationButton.setTextColor(redPrincipal)
            binding.editTextDate.visibility = View.VISIBLE
            binding.addressInputText.visibility = View.VISIBLE
            typePublication = 0
        }

        binding.publicationButton.setOnClickListener {
            binding.campaignButton.setBackgroundResource(R.drawable.red_stroke_bt)
            binding.campaignButton.setTextColor(redPrincipal)
            binding.publicationButton.setBackgroundResource(R.drawable.button_simple)
            binding.publicationButton.setTextColor(white)
            binding.editTextDate.visibility = View.INVISIBLE
            binding.addressInputText.visibility = View.INVISIBLE
            typePublication = 1
        }

        binding.publishButton.setOnClickListener {
            if (typePublication == 0) {
                val description = binding.editTextTextMultiLine.text.toString()
                val address = binding.addressInputText.text.toString()
                val date = binding.editTextDate.text.toString()

                if (description.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty()) {
                    val campaign = Campaign(null, institutionId, null, null, description, selectedImages, date, address)
                    lifecycleScope.launch {
                        campaignUseCase.createCampaign(campaign)
                    }
                    binding.editTextTextMultiLine.setText("")
                    binding.addressInputText.setText("")
                    binding.editTextDate.setText("")
                } else {
                    binding.editTextTextMultiLine.error = "Preencha todos os campos"
                    binding.addressInputText.error = "Preencha todos os campos"
                    binding.editTextDate.error = "Preencha todos os campos"
                }
            } else {
                val description = binding.editTextTextMultiLine.text.toString()

                if (description.isNotEmpty()) {
                    val publication = Publication(null, institutionId, null, null, description, selectedImages)
                    lifecycleScope.launch {
                        publicationUseCase.createPublication(publication)
                    }
                    binding.editTextTextMultiLine.setText("")
                } else {
                    binding.editTextTextMultiLine.error = "Preencha todos os campos"
                }
            }
        }
    }
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            selectedImages.clear()
            result.data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    if (i < 5) {
                        val imageUri = clipData.getItemAt(i).uri
                        val bitmap = imageUtils.getRotatedBitmap(requireContext(), imageUri)
                        val base64Image = imageUtils.bitmapToBase64(bitmap)
                        selectedImages.add(Image(id = "image_$i", image =  base64Image))
                    } else {
                        Toast.makeText(requireContext(), "Limite de 5 imagens", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            } ?: run {
                result.data?.data?.let { uri ->
                    val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    val base64Image = imageUtils.bitmapToBase64(bitmap)
                    selectedImages.add(Image(id = "image_0", image = base64Image))
                }
            }
            setupRecyclerView()
        }else{
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        galleryLauncher.launch(intent)
    }

    private fun setupRecyclerView() = binding.listImageRecycleView.apply {
        listImageAdapter = ListImagesAdapter()
        listImageAdapter!!.imagesList = selectedImages
        adapter = listImageAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }
}
