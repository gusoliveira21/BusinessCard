package com.gusoliveira21.loginBasic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.gusoliveira21.loginBasic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        servicosGoogle()

    }

    private fun servicosGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    fun signInGoogle(view: View){

        /*var conta: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if(conta == null){
            var intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 555)
        }else{
            Toast.makeText(this, "Já tem uma conta conectada!", Toast.LENGTH_SHORT).show()
        }*/
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, PrincipalActivity::class.java))
        }
    }

    fun login(view: View) {
        startActivity(Intent(this, LoginEmailActivity::class.java))
    }

    fun Cadastrar(view: View) {

        startActivity(Intent(this, CadastroActivity::class.java))

    }


}