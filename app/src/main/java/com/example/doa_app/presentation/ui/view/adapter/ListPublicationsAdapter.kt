package com.example.doa_app.presentation.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doa_app.R
import com.example.doa_app.data.model.mobile.PublicationMob
import com.example.doa_app.databinding.PublicationBinding
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.utils.ImageUtils

class ListPublicationsAdapter : RecyclerView.Adapter<ListPublicationsAdapter.ListPublicationViewHolder>() {
    private val imageUtils = ImageUtils()
    inner class ListPublicationViewHolder(val binding: PublicationBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<PublicationMob>() {
        override fun areItemsTheSame(oldItem: PublicationMob, newItem: PublicationMob): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PublicationMob, newItem: PublicationMob): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var publicationMobList: MutableList<PublicationMob>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }


    override fun getItemCount() = publicationMobList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPublicationViewHolder {
        return ListPublicationViewHolder(
            PublicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListPublicationViewHolder, position: Int) {
        holder.binding.apply {
            val publication = publicationMobList[position]

            institutionName.text = publication.institutionName
            institutionImage.setImageBitmap(imageUtils.base64ToBitmap(publication.institutionPhoto ?: ""))
            description.text = publication.description
            fun setupListImageRecyclerView() = imageListView.apply {
                val listImageAdapter = ListOfImagesAdapter()
                listImageAdapter.imagesList = publication.images
                adapter = listImageAdapter
                addItemDecoration(
                    SpacingOnSide(
                        resources.getDimension(R.dimen.recycler_view_item_space).toInt()
                    )
                )
                layoutManager =
                    LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
            }
            setupListImageRecyclerView()

        }
    }
}