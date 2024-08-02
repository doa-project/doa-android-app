package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityHomeInstitutionBinding
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.HomeUserFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserFragment

class HomeInstitutionActivity : AppCompatActivity(R.layout.activity_home_institution) {
    private lateinit var binding: ActivityHomeInstitutionBinding

    private lateinit var btHome: ImageButton
    private lateinit var btHomeLogo: ImageButton
    private lateinit var btAddPublication: ImageButton
    private lateinit var btUser: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeInstitutionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btHome = binding.btHome
        btHomeLogo = binding.btHomeLogo
        btAddPublication = binding.btAddPublication
        btUser = binding.btUser

        addClick()
    }
    private fun addClick() {
        btHome.setOnClickListener {
            btHome.setImageResource(R.drawable.home_icon_selected);
            btAddPublication.setImageResource(R.drawable.add_publication);
            btUser.setImageResource(R.drawable.user_circle);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeUserFragment>(R.id.fragment)
            }
        }
        btAddPublication.setOnClickListener {
            btAddPublication.setImageResource(R.drawable.add_publication_selected)
            btHome.setImageResource(R.drawable.home_icon)
            btUser.setImageResource(R.drawable.user_circle)

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
        btUser.setOnClickListener {
            btUser.setImageResource(R.drawable.user_circle_selected);
            btAddPublication.setImageResource(R.drawable.add_publication);
            btHome.setImageResource(R.drawable.home_icon);

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserFragment>(R.id.fragment)
            }
        }
    }
}