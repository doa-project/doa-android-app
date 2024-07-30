package com.example.doa_app.presentation.ui.view;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.doa_app.data.model.Campaign
import com.example.doa_app.databinding.CampaignBinding

private lateinit var imageRecycleAdapter: ListImagesAdapter
class ListCampaignAdapter : RecyclerView.Adapter<ListCampaignAdapter.ListCampaignViewHolder>()  {
        inner class ListCampaignViewHolder(val binding: CampaignBinding) : RecyclerView.ViewHolder(binding.root)

        private val diffCallback = object : DiffUtil.ItemCallback<Campaign>() {
            override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
                return oldItem == newItem
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)
        private var campaignsList: List<Campaign>
            get() = differ.currentList
            set(value) { differ.submitList(value) }

        override fun getItemCount() = campaignsList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCampaignViewHolder {
            return ListCampaignViewHolder(
                CampaignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }
        override fun onBindViewHolder(holder: ListCampaignViewHolder, position: Int) {
            holder.binding.apply {
                val campaigns = campaignsList[position]
                institutionName.text = campaigns.institutionName
                institutionImage.setImageBitmap(campaigns.institutionPhoto)
                description.text = campaigns.description
                local.text = campaigns.local
                date.text = campaigns.endDate
                imageRecycleAdapter = ListImagesAdapter()
                imageListView.adapter = imageRecycleAdapter
                imageRecycleAdapter.imagesList = campaigns.images

            }
        }
    }

