package com.gusoliveira21.businesscard.acesso

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gusoliveira21.businesscard.databinding.Intro3EntradaBinding

class MeiosDeAcessoActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: Intro3EntradaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Intro3EntradaBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }


}