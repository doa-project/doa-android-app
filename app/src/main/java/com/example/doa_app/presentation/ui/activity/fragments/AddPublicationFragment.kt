package com.example.doa_app.presentation.ui.activity.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doa_app.R
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.mobile.Image
import com.example.doa_app.databinding.FragmentAddPublicationBinding
import com.example.doa_app.presentation.ui.view.adapter.ListSelectedImagesAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.presentation.view_model.AddPublicationViewModel
import com.example.doa_app.utils.ImageUtils
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AddPublicationFragment : Fragment(R.layout.fragment_add_publication) {
    private var _binding: FragmentAddPublicationBinding? = null
    private val binding get() = _binding!!

    // ViewModel integration
    private val addPublicationViewModel = getViewModel<AddPublicationViewModel>()

        private var listImageAdapter: ListSelectedImagesAdapter? = null
    private val gson = Gson()
    private val imageUtils = ImageUtils()

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

        setupListImageRecyclerView()

        addPublicationViewModel.loadingVisibility.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = it
        }

        addPublicationViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            if (message != null) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }

        addPublicationViewModel.selectedImages.observe(viewLifecycleOwner) { images ->
            listImageAdapter?.imagesList = images.toMutableList()
            listImageAdapter?.notifyDataSetChanged()
        }

        arguments?.let {
            val data = it.getString("loggedUser")
            val institution = gson.fromJson(data, Institution::class.java)
            addPublicationViewModel.setInstitution(institution)
        }

        // Button clicks
        binding.galeryButton.setOnClickListener {
            openGallery()
            setupListImageRecyclerView()
        }

        binding.publishButton.setOnClickListener {
            addPublicationViewModel.createPublicationOrCampaign(
                description = binding.editTextTextMultiLine.text.toString(),
                address = binding.addressInputText.text.toString(),
                date = binding.editTextDate.text.toString()
            )
        }
    }

    private fun setupListImageRecyclerView() = binding.listImageRecycleView.apply {
        listImageAdapter = ListSelectedImagesAdapter()
        adapter = listImageAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        addPublicationViewModel.clearImages()
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            result.data?.clipData?.let { clipData ->
                for (i in 0 until clipData.itemCount) {
                    if (i < 5) {
                        val imageUri = clipData.getItemAt(i).uri
                        val bitmap = loadBitmapFromUri(imageUri)
                        val base64Image = imageUtils.bitmapToBase64(bitmap)
                        addPublicationViewModel.addImage(Image(
                            i.toString(),
                            base64Image
                        ))
                    } else {
                        Toast.makeText(requireContext(), "Limite de 5 imagens", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            } ?: run {
                result.data?.data?.let { uri ->
                    val bitmap = loadBitmapFromUri(uri)
                    val base64Image = imageUtils.bitmapToBase64(bitmap)
                    addPublicationViewModel.addImage(Image(
                        "0",
                        base64Image
                    ))
                }
            }
        } else {
            Toast.makeText(requireContext(), "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap {
        val contentResolver = requireContext().contentResolver
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        galleryLauncher.launch(intent)
    }
}
