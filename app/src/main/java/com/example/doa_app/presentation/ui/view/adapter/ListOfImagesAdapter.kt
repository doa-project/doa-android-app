package com.example.doa_app.presentation.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.doa_app.data.model.mobile.Image
import com.example.doa_app.databinding.PublicationImageBinding
import com.example.doa_app.utils.ImageUtils

class ListOfImagesAdapter : RecyclerView.Adapter<ListOfImagesAdapter.ListImageViewHolder>() {
    private val imageUtils = ImageUtils()
    inner class ListImageViewHolder(val binding: PublicationImageBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var imagesList: List<Image>
        get() = differ.currentList
        set(value) { differ.submitList(value) }

    override fun getItemCount() = imagesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListImageViewHolder {
        return ListImageViewHolder(PublicationImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }
    override fun onBindViewHolder(holder: ListImageViewHolder, position: Int) {
        holder.binding.apply {
            val images = imagesList[position]
            image.setImageBitmap(imageUtils.base64ToBitmap(images.image))
        }
    }
}