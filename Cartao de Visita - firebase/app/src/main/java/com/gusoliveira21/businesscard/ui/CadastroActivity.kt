package com.gusoliveira21.businesscard.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.ActivityCadastrarBinding
import com.gusoliveira21.businesscard.util.util

class CadastroActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityCadastrarBinding.inflate(layoutInflater) }

    //TODO: Na documentação não havia nada falando que precisava criar esta variável.
    // Mas é necessário para a função configuracaoGoogleSignIn()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        listeners()

    }

    /*public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            startActivity(Intent(this, PrincipalActivity::class.java))
    }*/


    private fun listeners() {
        binding.apply {
            btBack.setOnClickListener { finish() }
            btCadastrar.setOnClickListener { signInWithEmailAndPassword() }
            olhoAberto.setOnClickListener {
                binding.apply {
                    olhoAberto.visibility = View.INVISIBLE
                    olhoFechado.visibility = View.VISIBLE
                    campoSenhaUm.inputType = 144
                    campoSenhaDois.inputType = 144
                }
            }
            olhoFechado.setOnClickListener {
                binding.apply {
                    olhoFechado.visibility = View.GONE
                    olhoAberto.visibility = View.VISIBLE
                    campoSenhaUm.inputType = 129
                    campoSenhaDois.inputType = 129
                }
            }
        }
    }


    // -------------------------------- login com email e senha --------------------------------
    private fun signInWithEmailAndPassword() {
        if (verificaSenhas() && verificaSeEmailEEmpty()) {
            auth.createUserWithEmailAndPassword(
                binding.campoEmailLogin.text.toString(),
                binding.campoSenhaUm.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.e("TAG", "VERIFICAÇÃO: $auth.currentUser")
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT)
                            .show()
                        startActivity(Intent(this, PrincipalActivity::class.java))
                        finish()

                    } else {
                        Log.i(ContentValues.TAG, "mensagem: ${task.exception}")

                        //Todo: Preciso colocar a funçao opcoesErro na classe Util
                        util().opcoesErro(task.exception.toString())
                        //opcoesErro(task.exception.toString())

                    }
                }
        }

    }

    // -------------------------------- Util --------------------------------
    private fun verificaSenhas(): Boolean {
        if ((binding.campoSenhaUm.text.toString().isEmpty() or
                    binding.campoSenhaDois.text.toString().isEmpty()) == false
        )
            if (binding.campoSenhaUm.text.toString().equals(binding.campoSenhaDois.text.toString()))
                return true
        setMessageOfErrorPassword("Senhas não conferem!")
        return false
    }

    private fun opcoesErro(errorValue: String) {
        when {
            errorValue.contains("email address is already in use") ->
                binding.campoEmailLogin.setError("O endereço de email já está sendo usado em outra conta!")
            errorValue.contains("password is invalid") ->
                setMessageOfErrorPassword("Digite uma senha mais forte! \n A cima de 6 dígitos!")
            else -> Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun verificaSeEmailEEmpty(): Boolean {
        if (binding.campoEmailLogin.text!!.isEmpty() == false)
            return true
        setMessageOfErrorEmail()
        return false
    }

    private fun setMessageOfErrorEmail() {
        binding.campoEmailLogin.setError("Digite um email!")
    }

    private fun setMessageOfErrorPassword(mensagem: String) {
        binding.campoSenhaUm.setError(mensagem)
        binding.campoSenhaDois.setError(mensagem)
    }


}