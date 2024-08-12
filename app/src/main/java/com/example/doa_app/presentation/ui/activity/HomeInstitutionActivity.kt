package com.example.doa_app.presentation.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.data.model.api.Institution
import com.example.doa_app.databinding.ActivityHomeInstitutionBinding
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.ListPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserProfileFragment
import com.google.gson.Gson

class HomeInstitutionActivity : AppCompatActivity(R.layout.activity_home_institution) {
    private lateinit var binding: ActivityHomeInstitutionBinding

    private lateinit var btHome: ImageButton
    private lateinit var btHomeLogo: ImageButton
    private lateinit var btAddCreate: ImageButton
    private lateinit var btUser: ImageButton
    private lateinit var institutionLogged: Institution
    private val gson = Gson()

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

        val data = intent?.getStringExtra("loggedUser")
        if (data != null) {
            institutionLogged = gson.fromJson(data, Institution::class.java)
            Log.d("HomeInstitutionActivity", "onCreate: $institutionLogged")
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        addClick()
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

            val fragment = AddPublicationFragment()
            val bundle = Bundle()
            bundle.putString("loggedUser", gson.toJson(institutionLogged))
            fragment.arguments = bundle

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment, fragment)
            }
        }
        btUser.setOnClickListener {
            btHome.setImageResource(R.drawable.ihomen);
            btAddCreate.setImageResource(R.drawable.icreaten);
            btUser.setImageResource(R.drawable.iprofiles);

            val fragment = UserProfileFragment()
            val bundle = Bundle()
            bundle.putString("loggedInstitution", gson.toJson(institutionLogged))
            fragment.arguments = bundle

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.fragment, fragment)
            }
        }
    }
}