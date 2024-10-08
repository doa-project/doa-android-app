package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityHomeUserBinding
import com.example.doa_app.presentation.ui.activity.fragments.ListCampaignFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserProfileFragment
import com.example.doa_app.utils.SharedPreferences

class HomeUserActivity : AppCompatActivity(R.layout.activity_home_user) {
    private lateinit var binding: ActivityHomeUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ListCampaignFragment>(R.id.fragment)
        }

        binding.btHome.setOnClickListener {
            binding.btHome.setImageResource(R.drawable.ihomes);
            binding.btUser.setImageResource(R.drawable.iprofilen);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListCampaignFragment>(R.id.fragment, "ListCampaignFragment")
            }
        }
        binding.btUser.setOnClickListener {
            binding.btHome.setImageResource(R.drawable.ihomen);
            binding.btUser.setImageResource(R.drawable.iprofiles);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserProfileFragment>(R.id.fragment, "UserProfileFragment")
            }
        }
    }
}