package com.example.doa_app.utils

import com.example.doa_app.data.model.mobile.CampaignMob
import com.example.doa_app.data.model.api.CampaignAPI
import com.example.doa_app.data.model.mobile.Image
class TreatmentApiObjects {
    fun campaignToCampaignAPI(campaignMob: CampaignMob): CampaignAPI {
        val mutableList = mutableListOf<String>()
        for ((cont, image) in campaignMob.images.withIndex()) {
            mutableList.add(
                image.image
            )
        }
        return CampaignAPI(
            id = campaignMob.id,
            campaignId = campaignMob.campaignId,
            institutionId = campaignMob.institutionId,
            institutionName = campaignMob.institutionName,
            institutionPhoto = campaignMob.institutionPhoto,
            description = campaignMob.description,
            images = mutableList,
            endDate = campaignMob.endDate,
            local = campaignMob.local
        )
    }
    fun campaignApiToCampaign(campaignAPI: CampaignAPI): CampaignMob {
        val mutableList = mutableListOf<Image>()
        for ((cont, image) in campaignAPI.images.withIndex()) {
            mutableList.add(
                Image(
                    id = cont.toString(),
                    image = image
                )
            )
        }
        return CampaignMob(
            id = campaignAPI.id,
            campaignId = campaignAPI.campaignId,
            institutionId = campaignAPI.institutionId,
            institutionName = campaignAPI.institutionName,
            institutionPhoto = campaignAPI.institutionPhoto,
            description = campaignAPI.description,
            images = mutableList,
            endDate = campaignAPI.endDate,
            local = campaignAPI.local
        )
    }
    fun campaignApiToCampaignList(campaignAPIList: List<CampaignAPI>): MutableList<CampaignMob>{
        val mutableList = mutableListOf<CampaignMob>()
        for ((cont, campaign) in campaignAPIList.withIndex()) {
            mutableList.add(
                campaignApiToCampaign(campaign)
            )
        }
        return mutableList
    }
    fun imageListToStringList(imageList: List<Image>): MutableList<String>{
        val mutableList = mutableListOf<String>()
        for ((cont, image) in imageList.withIndex()) {
            mutableList.add(
                image.image
            )
        }
        return mutableList
    }
}