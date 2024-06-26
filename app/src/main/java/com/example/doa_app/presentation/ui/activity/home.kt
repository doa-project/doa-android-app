package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityMainBinding
import com.example.doa_app.presentation.ui.activity.fragments.fragment_add_publication
import com.example.doa_app.presentation.ui.activity.fragments.fragment_home
import com.example.doa_app.presentation.ui.activity.fragments.fragment_user

class home : AppCompatActivity(R.layout.activity_home) {

    private lateinit var bt_home: ImageButton
    private lateinit var bt_home2: ImageButton
    private lateinit var bt_add_publication: ImageButton
    private lateinit var bt_user: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_home = findViewById(R.id.bt_home)
        bt_home2 = findViewById(R.id.bt_home2)
        bt_add_publication = findViewById(R.id.bt_add_publication)
        bt_user = findViewById(R.id.bt_user)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<fragment_home>(R.id.fragment)
            }
        }

        addClick()
    }

    // função para adicionar as funções de cliques
    private fun addClick() {
        bt_home2.setOnClickListener {
            bt_home2.setImageResource(R.drawable.home_icon_selected);
            bt_add_publication.setImageResource(R.drawable.add_publication);
            bt_user.setImageResource(R.drawable.user_circle);

            // setando o fragmento para home
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<fragment_home>(R.id.fragment)
            }
        }
        bt_add_publication.setOnClickListener() {
            bt_add_publication.setImageResource(R.drawable.add_publication_selected);
            bt_home2.setImageResource(R.drawable.home_icon);
            bt_user.setImageResource(R.drawable.user_circle);

            // setando o fragmento para publicação
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<fragment_add_publication>(R.id.fragment)
            }
        }
        bt_user.setOnClickListener() {
            bt_user.setImageResource(R.drawable.user_circle_selected);
            bt_add_publication.setImageResource(R.drawable.add_publication);
            bt_home2.setImageResource(R.drawable.home_icon);

            // setando o fragmento para user
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<fragment_user>(R.id.fragment)
            }
        }
    }
}