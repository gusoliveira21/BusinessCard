package com.gusoliveira21.businesscard.ui.acesso.cadastrarUser

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.databinding.ActivityCadastrarBinding

class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityCadastrarBinding.inflate(layoutInflater) }

    //TODO: Na documentação não havia nada falando que precisava criar esta variável.
    // Mas é necessário para a função configuracaoGoogleSignIn()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configurationHideactionbarAndSystemInformation()
        auth = Firebase.auth
        listeners()

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


    private fun listeners() {
        binding.apply {
            btBack.setOnClickListener { finish() }
            btCadastrar.setOnClickListener { signInWithEmailAndPassword() }
            olhoAberto.setOnClickListener {
                binding.apply {
                    olhoAberto.visibility = View.INVISIBLE
                    olhoFechado.visibility = View.VISIBLE
                    campoSenha.inputType = 144
                    campoConfirmaSenha.inputType = 144
                }
            }
            olhoFechado.setOnClickListener {
                binding.apply {
                    olhoFechado.visibility = View.GONE
                    olhoAberto.visibility = View.VISIBLE
                    campoSenha.inputType = 129
                    campoConfirmaSenha.inputType = 129
                }
            }
        }
    }

    private fun configurationHideactionbarAndSystemInformation() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar!!.hide()
    }
    // -------------------------------- login com email e senha --------------------------------
    private fun signInWithEmailAndPassword() {
        if (verificaSenhas() && verificaSeEmailEEmpty()) {
            auth.createUserWithEmailAndPassword(
                binding.campoEmail.text.toString(),
                binding.campoSenha.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("TAG", "VERIFICAÇÃO: $auth.currentUser")
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, PrincipalActivity::class.java))
                        finish()

                    } else {
                        Log.i(ContentValues.TAG, "mensagem: ${task.exception}")

                        opcoesErro(task.exception.toString())

                    }
                }
        }

    }

    // -------------------------------- Util --------------------------------
    private fun verificaSenhas(): Boolean {
        if ((binding.campoSenha.text.toString().isEmpty() or
                    binding.campoConfirmaSenha.text.toString().isEmpty()) == false
        )
            if (binding.campoSenha.text.toString().equals(binding.campoConfirmaSenha.text.toString()))
                return true
        setMessageOfErrorPassword("Senhas não conferem!")
        return false
    }
//TODO: Colocar na Util
    private fun opcoesErro(errorValue: String) {
        when {
            errorValue.contains("email address is already in use") ->
                binding.campoEmail.setError("O endereço de email já está sendo usado em outra conta!")
            errorValue.contains("password is invalid") ->
                setMessageOfErrorPassword("Digite uma senha mais forte! \n A cima de 6 dígitos!")
            else -> Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT)
                .show()
        }
    }
//TODO: Colocar na Util
    private fun verificaSeEmailEEmpty(): Boolean {
        if (binding.campoEmail.text!!.isEmpty() == false)
            return true
        setMessageOfErrorEmail()
        return false
    }

    //TODO: Colocar na Util
    private fun setMessageOfErrorEmail() {
        binding.campoEmail.setError("Digite um email!")
    }
//TODO: Colocar na Util
    private fun setMessageOfErrorPassword(mensagem: String) {
        binding.campoSenha.setError(mensagem)
        binding.campoConfirmaSenha.setError(mensagem)
    }


}