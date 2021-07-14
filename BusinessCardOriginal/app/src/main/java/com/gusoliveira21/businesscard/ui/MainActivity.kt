package com.gusoliveira21.businesscard.ui

import android.content.Intent
import android.icu.text.RelativeDateTimeFormatter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gusoliveira21.businesscard.AddBusinessCardActivity
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private lateinit var binding = ActivityMainBinding
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btAddCard.setOnClickListener {
        val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
        startActivity(intent)
        }
    }
}