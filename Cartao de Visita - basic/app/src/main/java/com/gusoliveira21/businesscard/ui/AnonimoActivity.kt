package com.gusoliveira21.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.gusoliveira21.businesscard.R

class AnonimoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anonimo)


        findViewById<Button>(R.id.buttonAnonimo).setOnClickListener {
            Toast.makeText(this, "Tela de Anonimato", Toast.LENGTH_SHORT).show()
        }
    }
}