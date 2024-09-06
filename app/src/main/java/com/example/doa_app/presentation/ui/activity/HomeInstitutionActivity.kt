package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityHomeInstitutionBinding
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.ListPublicationFragment
import com.example.doa_app.utils.SharedPreferences

class HomeInstitutionActivity : AppCompatActivity(R.layout.activity_home_institution) {
    private lateinit var binding: ActivityHomeInstitutionBinding

    private lateinit var btHome: ImageButton
    private lateinit var btHomeLogo: ImageButton
    private lateinit var btAddCreate: ImageButton
    private lateinit var btUser: ImageButton

    private val sharedPref = SharedPreferences(this, "login")
    private val sharedPrefCurrentCampaign = SharedPreferences(this, "currentCampaign")
    private val sharedPrefCurrentPublication = SharedPreferences(this, "currentPublication")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeInstitutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btHome = binding.btHome
        btHomeLogo = binding.btHomeLogo
        btAddCreate = binding.btAddPublication
        btUser = binding.btUser

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ListPublicationFragment>(R.id.fragment)
        }
        addClick()
    }

    override fun onDestroy() {
        sharedPref.clearAll()
        sharedPrefCurrentCampaign.clearAll()
        sharedPrefCurrentPublication.clearAll()
        super.onDestroy()
    }
    private fun addClick() {
        btHome.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomes);
            btAddCreate.setImageResource(R.drawable.icreaten);
            btUser.setImageResource(R.drawable.iprofilen);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<ListPublicationFragment>(R.id.fragment)
            }
        }
        btAddCreate.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomen);
            btAddCreate.setImageResource(R.drawable.icreates);
            btUser.setImageResource(R.drawable.iprofilen);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
        btUser.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomen);
            btAddCreate.setImageResource(R.drawable.icreaten);
            btUser.setImageResource(R.drawable.iprofiles);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
    }
}