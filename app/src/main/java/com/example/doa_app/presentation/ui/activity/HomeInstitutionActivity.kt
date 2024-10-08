package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityHomeInstitutionBinding
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.ListCampaignFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserProfileFragment
import com.example.doa_app.utils.SharedPreferences

class HomeInstitutionActivity : AppCompatActivity(R.layout.activity_home_institution) {
    private lateinit var binding: ActivityHomeInstitutionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeInstitutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.btHome.setImageResource(R.drawable.ihomes)
            binding.btAddPublication.setImageResource(R.drawable.icreaten)
            binding.btUser.setImageResource(R.drawable.iprofilen)


        binding.btHome.setOnClickListener {
            binding.btHome.setImageResource(R.drawable.ihomes)
            binding.btAddPublication.setImageResource(R.drawable.icreaten)
            binding.btUser.setImageResource(R.drawable.iprofilen)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListCampaignFragment>(R.id.fragment)
            }
        }
        binding.btAddPublication.setOnClickListener {
            binding.btHome.setImageResource(R.drawable.ihomen)
            binding.btAddPublication.setImageResource(R.drawable.icreates)
            binding.btUser.setImageResource(R.drawable.iprofilen)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
        binding.btUser.setOnClickListener {
            binding.btHome.setImageResource(R.drawable.ihomen)
            binding.btAddPublication.setImageResource(R.drawable.icreaten)
            binding.btUser.setImageResource(R.drawable.iprofiles)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserProfileFragment>(R.id.fragment)
            }
        }
    }
}