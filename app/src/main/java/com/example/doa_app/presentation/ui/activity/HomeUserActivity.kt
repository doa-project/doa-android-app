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
import com.example.doa_app.presentation.ui.activity.fragments.UserFragment

class HomeUserActivity : AppCompatActivity() {
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

    }
    private fun addClick() {
        btHome.setOnClickListener {
            btHome.setImageResource(R.drawable.home_icon_selected);
            btViewCampaign.setImageResource(R.drawable.add_publication);
            btUser.setImageResource(R.drawable.donationicon);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeUserFragment>(R.id.fragment)
            }
        }
        btViewCampaign.setOnClickListener {
            btViewCampaign.setImageResource(R.drawable.donationselected)
            btHome.setImageResource(R.drawable.home_icon)
            btUser.setImageResource(R.drawable.user_circle)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
        btUser.setOnClickListener {
            btUser.setImageResource(R.drawable.user_circle_selected);
            btViewCampaign.setImageResource(R.drawable.add_publication);
            btHome.setImageResource(R.drawable.home_icon);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserFragment>(R.id.fragment)
            }
        }
    }
}