package com.example.doa_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.doa_app.R
import com.example.doa_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}