package com.gusoliveira21.loginBasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.loginBasic.databinding.ActivityLoginEmailBinding
import com.gusoliveira21.loginBasic.databinding.ActivityPrincipalBinding

class PrincipalActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityPrincipalBinding.inflate(layoutInflater)}
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


    }


    fun deslogarUsuario(view: View){
        auth.signOut()
        finish()
    }


    fun recuperaSenha(view:View){

    }




}