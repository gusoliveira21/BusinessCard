package com.gusoliveira21.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.gusoliveira21.businesscard.R

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)


        findViewById<Button>(R.id.buttonRegistro).setOnClickListener {
            Toast.makeText(this, "Tela de Cadastro", Toast.LENGTH_SHORT).show()
        }

    }
}