package com.example.doa_app.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.doa_app.R
import com.example.doa_app.presentation.ui.activity.fragments.AddPublicationFragment
import com.example.doa_app.presentation.ui.activity.fragments.HomeUserFragment
import com.example.doa_app.presentation.ui.activity.fragments.UserFragment

class HomeUserActivity : AppCompatActivity(R.layout.activity_home_user) {

    private lateinit var bt_home: ImageButton
    private lateinit var bt_home_logo: ImageButton
    private lateinit var bt_add_publication: ImageButton
    private lateinit var bt_user: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bt_home = findViewById(R.id.bt_home)
        bt_home_logo = findViewById(R.id.bt_home_logo)
        bt_add_publication = findViewById(R.id.bt_add_publication)
        bt_user = findViewById(R.id.bt_user)

        addClick()
    }

    // função para adicionar as funções de cliques
    private fun addClick() {
        bt_home.setOnClickListener {
            bt_home.setImageResource(R.drawable.home_icon_selected);
            bt_add_publication.setImageResource(R.drawable.add_publication);
            bt_user.setImageResource(R.drawable.user_circle);

            // setando o fragmento para home
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<HomeUserFragment>(R.id.fragment)
            }
        }
        bt_add_publication.setOnClickListener {
            bt_add_publication.setImageResource(R.drawable.add_publication_selected);
            bt_home.setImageResource(R.drawable.home_icon);
            bt_user.setImageResource(R.drawable.user_circle);

            // setando o fragmento para publicação
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<AddPublicationFragment>(R.id.fragment)
            }
        }
        bt_user.setOnClickListener {
            bt_user.setImageResource(R.drawable.user_circle_selected);
            bt_add_publication.setImageResource(R.drawable.add_publication);
            bt_home.setImageResource(R.drawable.home_icon);

            // setando o fragmento para user
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UserFragment>(R.id.fragment)
            }
        }
    }
}