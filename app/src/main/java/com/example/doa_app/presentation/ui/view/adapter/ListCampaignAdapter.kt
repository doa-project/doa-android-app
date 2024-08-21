package com.example.doa_app.presentation.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doa_app.R
import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.databinding.CampaignBinding
import com.example.doa_app.presentation.ui.view.style.SpacingOnSide
import com.example.doa_app.utils.ImageUtils

class ListCampaignAdapter: RecyclerView.Adapter<ListCampaignAdapter.ListCampaignViewHolder>()  {
    private val imageUtils = ImageUtils()
    inner class ListCampaignViewHolder(val binding: CampaignBinding) : RecyclerView.ViewHolder(binding.root)

        private val diffCallback = object : DiffUtil.ItemCallback<CampaignMob>() {
            override fun areItemsTheSame(oldItem: CampaignMob, newItem: CampaignMob): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: CampaignMob, newItem: CampaignMob): Boolean {
                return oldItem == newItem
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)
    var campaignsList: MutableList<CampaignMob>
            get() = differ.currentList
            set(value) { differ.submitList(value) }

        override fun getItemCount() = campaignsList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCampaignViewHolder {
            return ListCampaignViewHolder(
                CampaignBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

        }
        override fun onBindViewHolder(holder: ListCampaignViewHolder, position: Int) {
            holder.binding.apply {
                val campaigns = campaignsList[position]
                institutionName.text = campaigns.institutionName
                institutionImage.setImageBitmap(imageUtils.base64ToBitmap((campaigns.institutionPhoto ?: "")))
                description.text = campaigns.description
                local.text = campaigns.local
                date.text = campaigns.endDate
                fun setupListImageRecyclerView() = imageListView.apply {
                    val listImageAdapter = ListImagesPublicationsAdapter()
                    listImageAdapter.imagesList = campaigns.images
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


//                institutionImage.setOnClickListener {
//                    listener.onCampaignClick(campaigns)
//                }
//                institutionName.setOnClickListener {
//                    listener.onCampaignClick(campaigns)
//                }
            }
        }
    }