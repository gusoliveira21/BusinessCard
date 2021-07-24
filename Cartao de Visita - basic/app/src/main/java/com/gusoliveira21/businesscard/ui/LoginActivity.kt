package com.gusoliveira21.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.gusoliveira21.businesscard.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.buttonToast).setOnClickListener {
            Toast.makeText(this, "Tela de login", Toast.LENGTH_SHORT).show()
        }

    }
}