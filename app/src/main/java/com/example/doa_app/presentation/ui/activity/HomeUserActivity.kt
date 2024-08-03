package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityHomeUserBinding
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.HomeUserFragment
import com.example.doa_app.presentation.ui.activity.fragments.ListCampaignFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserProfileFragment

class HomeUserActivity : AppCompatActivity(R.layout.activity_home_user) {
    private lateinit var binding: ActivityHomeUserBinding

    private lateinit var btHome: ImageButton
    private lateinit var btHomeLogo: ImageButton
    private lateinit var btViewCampaign: ImageButton
    private lateinit var btUser: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btHome = binding.btHome
        btHomeLogo = binding.btHomeLogo
        btViewCampaign = binding.btViewCampaign
        btUser = binding.btUser

        addClick()

    }
    private fun addClick() {
        btHome.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomes);
            btViewCampaign.setImageResource(R.drawable.icampaignn);
            btUser.setImageResource(R.drawable.iprofilen);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeUserFragment>(R.id.fragment)
            }
        }
        btViewCampaign.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomen);
            btViewCampaign.setImageResource(R.drawable.icampaigns);
            btUser.setImageResource(R.drawable.iprofilen);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListCampaignFragment>(R.id.fragment)
            }
        }
        btUser.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomen);
            btViewCampaign.setImageResource(R.drawable.icampaignn);
            btUser.setImageResource(R.drawable.iprofiles);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserProfileFragment>(R.id.fragment)
            }
        }
    }
}