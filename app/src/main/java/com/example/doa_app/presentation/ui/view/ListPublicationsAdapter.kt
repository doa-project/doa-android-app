package com.example.doa_app.presentation.ui.view;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.doa_app.data.model.Publication
import com.example.doa_app.databinding.PublicationBinding

private lateinit var imageRecycleAdapter: ListImagesAdapter

class ListPublicationsAdapter : RecyclerView.Adapter<ListPublicationsAdapter.ListPublicationViewHolder>()  {
        inner class ListPublicationViewHolder(val binding: PublicationBinding) : RecyclerView.ViewHolder(binding.root)

        private val diffCallback = object : DiffUtil.ItemCallback<Publication>() {
            override fun areItemsTheSame(oldItem: Publication, newItem: Publication): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Publication, newItem: Publication): Boolean {
                return oldItem == newItem
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)
        private var publicationList: List<Publication>
            get() = differ.currentList
            set(value) { differ.submitList(value) }

        override fun getItemCount() = publicationList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPublicationViewHolder {
            return ListPublicationViewHolder(
                PublicationBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ))
        }
        override fun onBindViewHolder(holder: ListPublicationViewHolder, position: Int) {
            holder.binding.apply {
                val publication = publicationList[position]
                institutionName.text = publication.institutionName
                institutionImage.setImageBitmap(publication.institutionPhoto)
                imageRecycleAdapter = ListImagesAdapter()
                imageListView.adapter = imageRecycleAdapter
                imageRecycleAdapter.imagesList = publication.images!!

            }
        }
    }