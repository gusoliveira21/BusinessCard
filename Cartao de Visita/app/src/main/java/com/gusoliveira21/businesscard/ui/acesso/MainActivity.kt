package com.gusoliveira21.businesscard.ui.acesso

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.ui.acesso.cadastrarUser.CadastroActivity
import com.gusoliveira21.businesscard.ui.acesso.loginUser.LoginActivity
import com.gusoliveira21.businesscard.util.util

class MainActivity() : AppIntro() {
    //private  val binding by lazy { ActivityIntro3AcessoBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    //TODO: Na documentação não havia nada falando que precisava criar esta variável.
    // Mas é necessário para a função configuracaoGoogleSignIn()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configuracoesSlide()
        slideIntroducao()

        auth = FirebaseAuth.getInstance()
        configuracaoGoogleSignIn()

    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            startActivity(Intent(this, PrincipalActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        configuracoesSlide()
        slideIntroducao()

        super.onResume()
    }



    

    //Todo: Preciso colocar a verificação de internet no código
    //-------------------------------- Configurações do Slide --------------------------------
    fun configuracoesSlide() {
        setImmersiveMode()
        isSkipButtonEnabled = false
        isButtonsEnabled = false
        setTransformer(AppIntroPageTransformerType.Fade)
    }
    fun slideIntroducao() {
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.activity_intro_1_hello))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.activity_intro_2_description))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.activity_intro_3_acesso))
    }
    // -------------------------------- login com facebook --------------------------------
    fun signInWithFacebook(view: View) {
        Toast.makeText(this, "Ainda não implementado", Toast.LENGTH_SHORT).show()
    }
    // -------------------------------- login com github --------------------------------
    fun signInWithGithub(view: View) {
        Toast.makeText(this, "Ainda não implementado", Toast.LENGTH_SHORT).show()
    }










    // -------------------------------- login com o google --------------------------------

    //TODO: Configuração do google sigin
    private fun configuracaoGoogleSignIn() {
        Log.e("TAG", "-> Configuração do google sigin")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    //TODO: Acessar
    fun signInWithGoogle(view: View) {
        if (util.statusInternet(this)) {
        Toast.makeText(this, "Acessando login com o google!", Toast.LENGTH_LONG).show()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 555)
        } else Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_LONG).show()
    }

    //TODO: Recebe o resultado da operação
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 555) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.e(ContentValues.TAG, "-> onActivityResultGoogle: sucesso")
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)

            } catch (e: ApiException) {
                Log.e(ContentValues.TAG, "-> onActivityResultGoogle: falha  -> ${task.exception}")
            }
        }
    }

    //TODO: Faz a autenticação
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(ContentValues.TAG, "-> firebaseAuthWithGoogle: Sucesso ")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                    Log.e(ContentValues.TAG,
                        "-> firebaseAuthWithGoogle: Falha -> ${task.exception}")
                }
            }
    }











    //-------------------------------- Botões login ou registro --------------------------------
    fun btLogin(view: View) {
        if (util.statusInternet(this)) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_LONG).show()
    }
    fun btCadastrar(view: View) {
        if (util.statusInternet(this)) {
            startActivity(Intent(this, CadastroActivity::class.java))
        } else
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_LONG).show()
    }

    //-------------------------------- Login anônimo --------------------------------
    fun btUseAnonimamente(view: View) {
        if (util.statusInternet(this)) {
            Log.e("TAG", "currentUser antes -> ${auth.currentUser}")
            auth.signInAnonymously().addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(baseContext, "Acessando anonimamente!", Toast.LENGTH_SHORT)
                            .show()
                        Log.w("TAG", "signInAnonymously:Sucess", task.exception)

                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Log.w("TAG", "Houve uma falha na autenticação -> ", task.exception)
                        Toast.makeText(baseContext, "Houve uma falha na autenticação!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        } else
            Toast.makeText(this, "Sem conexão com a internet!", Toast.LENGTH_LONG).show()

        }



}