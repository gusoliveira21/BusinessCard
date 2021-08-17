package com.gusoliveira21.loginBasic

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
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

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }



    //------------------------------------------------------------------------------------
    //TODO: Configuração do google sigin
    private fun servicosGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    /*fun loginComGoogle(view: View){

        var conta: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if(conta == null){

            var intent = googleSignInClient.signInIntent
            startActivityForResult(intent, 555)

        }else{
            Toast.makeText(this, "Já tem uma conta conectada!", Toast.LENGTH_SHORT).show()
        }
    }*/

    //TODO: Acessar
    fun loginComGoogle(view: View) {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 555)
    }

    //TODO: Recebe o resultado da operação
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 555) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                startActivity(Intent(this, PrincipalActivity::class.java))
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    //TODO: Faz a autenticação
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    updateUI(null)
                }
            }
    }
    //TODO: Verifica se o usuário está conectado
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, PrincipalActivity::class.java))
            finish()
        }
    }


    //------------------------------------------------------------------------------------

    fun login(view: View) {
        startActivity(Intent(this, LoginEmailActivity::class.java))
    }

    fun Cadastrar(view: View) {

        startActivity(Intent(this, CadastroActivity::class.java))

    }


}