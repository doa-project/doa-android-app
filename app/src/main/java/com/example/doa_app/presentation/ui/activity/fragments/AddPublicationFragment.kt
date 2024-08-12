package com.example.doa_app.presentation.ui.activity.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.format.DateFormat.getDateFormat
import android.util.Log
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
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.mobile.Image
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.data.model.api.PublicationAPI
import com.example.doa_app.data.repository.CampaignRepositoryImpl
import com.example.doa_app.data.repository.PublicationRepositoryImpl
import com.example.doa_app.databinding.FragmentAddPublicationBinding
import com.example.doa_app.domain.usecase.CampaignUseCase
import com.example.doa_app.domain.usecase.PublicationUseCase
import com.example.doa_app.presentation.ui.view.adapter.ListImagesAdapter
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.utils.ImageUtils
import com.example.doa_app.utils.TreatmentApiObjects
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeoutException

class AddPublicationFragment : Fragment(R.layout.fragment_add_publication) {
    private var _binding: FragmentAddPublicationBinding? = null
    private val binding get() = _binding!!

    private var typePublication = 1

    private var listImageAdapter: ListImagesAdapter? = null
    private var campaignUseCase = CampaignUseCase(CampaignRepositoryImpl(RetrofitInstance.service))
    private var publicationUseCase = PublicationUseCase(PublicationRepositoryImpl(RetrofitInstance.service))
    private val imageUtils = ImageUtils()
    private val selectedImages = mutableListOf<Image>()
    private lateinit var  institutionLogged: Institution
    private val treatmentApiObjects = TreatmentApiObjects()

    private val gson = Gson()

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
        arguments?.let {
            val data = it.getString("loggedUser")
            val dataJson = gson.fromJson(data, Institution::class.java)
            institutionLogged = dataJson
        }

        val redPrincipal = ContextCompat.getColor(requireContext(), R.color.red_principal)
        val white = ContextCompat.getColor(requireContext(), R.color.white)

        binding.editTextDate.visibility = View.INVISIBLE
        binding.addressInputText.visibility = View.INVISIBLE

//        val gridLayoutManager = GridLayoutManager(view.context, 1)
//        gridLayoutManager.orientation = GridLayoutManager.HORIZONTAL
//        binding.listImageRecycleView.layoutManager = gridLayoutManager

        binding.galeryButton.setOnClickListener {
            selectedImages.clear()
            openGallery()
        }

        binding.campaignButton.setOnClickListener {
            typePublication = 2
            binding.campaignButton.setBackgroundResource(R.drawable.button_simple)
            binding.campaignButton.setTextColor(white)
            binding.publicationButton.setBackgroundResource(R.drawable.red_stroke_bt)
            binding.publicationButton.setTextColor(redPrincipal)
            binding.editTextDate.visibility = View.VISIBLE
            binding.addressInputText.visibility = View.VISIBLE
        }

        binding.publicationButton.setOnClickListener {
            typePublication = 1
            binding.campaignButton.setBackgroundResource(R.drawable.red_stroke_bt)
            binding.campaignButton.setTextColor(redPrincipal)
            binding.publicationButton.setBackgroundResource(R.drawable.button_simple)
            binding.publicationButton.setTextColor(white)
            binding.editTextDate.visibility = View.INVISIBLE
            binding.addressInputText.visibility = View.INVISIBLE
        }

        binding.publishButton.setOnClickListener {
            showLoading()
            if (typePublication == 2) {
                val description = binding.editTextTextMultiLine.text.toString()
                val address = binding.addressInputText.text.toString()
                val date = binding.editTextDate.text.toString()

                if (selectedImages.size >= 1) {
                    if (description.isNotEmpty() && address.isNotEmpty() && date.isNotEmpty()) {
                        val imageStringList = treatmentApiObjects.imageListToStringList(selectedImages).toList()
                        try {
                            val datePattern = "^([0-2][0-9]|(3)[0-1])/([0][1-9]|(1)[0-2])/((19|20)\\d\\d)$".toRegex()
                            if (date.matches(datePattern)) {
                                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val parsedDate = formatter.parse(date)

                                val currentDate = Date()

                                if (parsedDate != null && parsedDate.after(currentDate)) {

                                    val campaign = CampaignAPI(
                                        null,
                                        null,
                                        institutionLogged.institutionId.toString(),
                                        null,
                                        null,
                                        description,
                                        imageStringList,
                                        date,
                                        address
                                    )

                                    lifecycleScope.launch {
                                        try {
                                            Log.d(
                                                "createCampaign",
                                                "Campaign Data: ${Gson().toJson(campaign)}"
                                            )
                                            campaignUseCase.createCampaign(campaign).let {
                                                hideLoading()
                                                if (it.body() == null) {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Error creating campaign",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    Log.d("createCampaign", it.toString())
                                                    Log.d(
                                                        "createCampaign",
                                                        "Response code: ${it.code()}"
                                                    )
                                                    Log.d(
                                                        "createCampaign",
                                                        "Response body: ${it.body()}"
                                                    )
                                                    Log.d(
                                                        "createCampaign",
                                                        "Response error body: ${
                                                            it.errorBody()?.string()
                                                        }"
                                                    )
                                                    return@launch
                                                } else {
                                                    Toast.makeText(
                                                        requireContext(),
                                                        "Campaign created successfully!",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    Log.d("createCampaign", it.toString())
                                                    Log.d(
                                                        "createCampaign",
                                                        "Response code: ${it.code()}"
                                                    )
                                                    Log.d(
                                                        "createCampaign",
                                                        "Response body: ${it.body()}"
                                                    )
                                                    binding.editTextTextMultiLine.setText("")
                                                    binding.addressInputText.setText("")
                                                    binding.editTextDate.setText("")
                                                    selectedImages.clear()
                                                    setupListImageRecyclerView()
                                                }
                                            }
                                        } catch (e: IOException) {
                                            Log.e(
                                                ContentValues.TAG,
                                                "IOException, you might not have internet connection: ${e.message}"
                                            )
                                            Toast.makeText(
                                                requireContext(),
                                                "You might not have internet connection",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            hideLoading()
                                            return@launch
                                        } catch (e: TimeoutException) {
                                            Log.e(
                                                ContentValues.TAG,
                                                "TimeoutException, request timed out: ${e.message}"
                                            )
                                            Toast.makeText(
                                                requireContext(),
                                                "Request timed out",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            hideLoading()
                                            return@launch
                                        }
                                        catch (e: InternalError){
                                            Log.e(ContentValues.TAG, "InternalError, an error occurred: ${e.message}")
                                            Toast.makeText(requireContext(), "Internal error", Toast.LENGTH_SHORT).show()
                                            hideLoading()
                                            return@launch
                                        }
                                        catch (e: Exception) {
                                            Log.e(
                                                ContentValues.TAG,
                                                "Exception, an error occurred: ${e.message}"
                                            )
                                            Toast.makeText(
                                                requireContext(),
                                                "An error occurred",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            hideLoading()
                                            return@launch
                                        }
                                    }
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        "The date must be in the future",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    hideLoading()
                                }
                            }else{
                                Toast.makeText(requireContext(), "Invalid date format", Toast.LENGTH_SHORT).show()
                                hideLoading()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Invalid date format", Toast.LENGTH_SHORT).show()
                            hideLoading()
                        }
                    } else {
                        binding.editTextTextMultiLine.error = "Fill all the fields"
                        binding.addressInputText.error = "Fill all the fields"
                        binding.editTextDate.error = "Fill all the fields"
                        hideLoading()
                    }
                } else {
                    Toast.makeText(requireContext(), "Select at least one image", Toast.LENGTH_SHORT).show()
                    hideLoading()
                }
            }
            else if (typePublication == 1) {
                val description = binding.editTextTextMultiLine.text.toString()
                if(selectedImages.size>=1) {
                    if (description.isNotEmpty()) {
                        val imageStringList = treatmentApiObjects.imageListToStringList(selectedImages).toList()
                        val publication = PublicationAPI(
                            null,
                            null,
                            institutionLogged.institutionId.toString(),
                            null,
                            null,
                            description,
                            imageStringList
                        )
                        lifecycleScope.launch {
                            try {
                                publicationUseCase.createPublication(publication).let {
                                    if (it.body() == null) {
                                        Toast.makeText(requireContext(), "Error creating campaign", Toast.LENGTH_SHORT).show()
                                        Log.d("createCampaign", it.toString())
                                        Log.d("createCampaign", "Response code: ${it.code()}")
                                        Log.d("createCampaign", "Response body: ${it.body()}")
                                        Log.d("createCampaign", "Response error body: ${it.errorBody()?.string()}")
                                        return@launch
                                    }else{
                                        hideLoading()
                                        Toast.makeText(requireContext(), "Publication created successfully!", Toast.LENGTH_SHORT).show()
                                        Log.d("createCampaign", it.toString())
                                        Log.d("createCampaign", "Response code: ${it.code()}")
                                        Log.d("createCampaign", "Response body: ${it.body()}")
                                        binding.editTextTextMultiLine.setText("")
                                        binding.addressInputText.setText("")
                                        binding.editTextDate.setText("")
                                        selectedImages.clear()
                                        setupListImageRecyclerView()
                                    }
                                }
                            }
                            catch (e: IOException) {
                                Log.e(ContentValues.TAG, "IOException, you might not have internet connection: ${e.message}")
                                Toast.makeText(requireContext(), "You might not have internet connection", Toast.LENGTH_SHORT).show()
                                hideLoading()
                                return@launch
                            } catch (e: TimeoutException) {
                                Log.e(ContentValues.TAG, "TimeoutException, request timed out: ${e.message}")
                                Toast.makeText(requireContext(), "Request timed out", Toast.LENGTH_SHORT).show()
                                hideLoading()
                                return@launch
                            }
                            catch (e: InternalError){
                                Log.e(ContentValues.TAG, "InternalError, an error occurred: ${e.message}")
                                Toast.makeText(requireContext(), "Internal error", Toast.LENGTH_SHORT).show()
                                hideLoading()
                                return@launch
                            }
                            catch (e: Exception) {
                                Log.e(ContentValues.TAG, "Exception, an error occurred: ${e.message}")
                                Toast.makeText(requireContext(), "An error occurred", Toast.LENGTH_SHORT).show()
                                hideLoading()
                                return@launch
                            }
                        }
                    } else {
                        binding.editTextTextMultiLine.error = "Fill all the fields"
                        hideLoading()
                    }
                } else{
                    Toast.makeText(requireContext(), "Select at least one image", Toast.LENGTH_SHORT).show()
                    hideLoading()
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
                        val bitmap = imageUtils.uriToBitmap (requireContext(), imageUri)
                        val base64Image = imageUtils.bitmapToBase64(bitmap)
                        selectedImages.add(Image(id = "image_$i", image = base64Image))
                    } else {
                        Toast.makeText(requireContext(), "Limite de 5 imagens", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            } ?: run {
                result.data?.data?.let { uri ->
                    val bitmap = loadBitmapFromUri(uri)
                    val base64Image = imageUtils.bitmapToBase64(bitmap)
                    selectedImages.add(Image(id = "image_0", image = base64Image))
                }
            }
            setupListImageRecyclerView()
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

    private fun setupListImageRecyclerView() = binding.listImageRecycleView.apply {
        listImageAdapter = ListImagesAdapter()
        listImageAdapter!!.imagesList = selectedImages
        adapter = listImageAdapter
        addItemDecoration(SpacingOnSide(resources.getDimension(R.dimen.recycler_view_item_space).toInt()))
        layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}
