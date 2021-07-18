package com.gusoliveira21.businesscard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.gusoliveira21.businesscard.App
import com.gusoliveira21.businesscard.databinding.ActivityMainBinding
import com.gusoliveira21.businesscard.util.Image

class MainActivity : AppCompatActivity() {
    //private lateinit var binding = ActivityMainBinding
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    //TODO comment: mainViewModel é responsavel por deixar o viewModel respondendo para a view
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }


    private val adapter by lazy { BusinessCardAdapter()}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rvCards.adapter = adapter
        getAllBusinessCard()
        insertListener()
    }

    private fun insertListener() {
        binding.btAddCard.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }
        adapter.listnerShare = {card ->
            Image.share(this@MainActivity, card)
        }

    }

    private fun getAllBusinessCard(){
        mainViewModel.getAll().observe(this, {businessCards ->
            adapter.submitList(businessCards)
        })
    }

}