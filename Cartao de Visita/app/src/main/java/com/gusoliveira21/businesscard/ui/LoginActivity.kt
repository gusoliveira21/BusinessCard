package com.gusoliveira21.businesscard.ui

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configurationHideactionbarAndSystemInformation()
        auth = FirebaseAuth.getInstance()
        listeners()

    }

    private fun configurationHideactionbarAndSystemInformation() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar!!.hide()
    }

    private fun listeners() {
        binding.btBack.setOnClickListener { finish() }


        binding.olhoAberto.setOnClickListener {
            binding.olhoAberto.visibility = View.INVISIBLE
            binding.olhoFechado.visibility = View.VISIBLE
            binding.campoSenha.inputType = 144
            Log.e("TAG", "Aberto: ${binding.campoSenha.inputType}")

        }

        binding.olhoFechado.setOnClickListener {
            binding.olhoFechado.visibility = View.GONE
            binding.olhoAberto.visibility = View.VISIBLE
            binding.campoSenha.inputType = 129
            Log.e("TAG", "Fechado: ${binding.campoSenha.inputType}")
        }
    }

    // -------------------------------- login com email e senha --------------------------------
    fun login(view: View) {
        if (verificarCampoSenha() && verificarCampoEmail()) {
            auth.signInWithEmailAndPassword(
                binding.campoEmail.text.toString(),
                binding.campoSenha.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Acessando!", Toast.LENGTH_SHORT).show()
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
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
    fun opcoesErro(mensagemErro: String) {
        if (mensagemErro.contains("badly formatted")) binding.campoEmail.setError("Forneça corretamente o email!")
        else if (mensagemErro.contains("user may have been deleted")) binding.campoEmail.setError("Email não cadastrado!")
        else if (mensagemErro.contains("password is invalid")) binding.campoEmail.setError("Senha incorreta!")
        else Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
    }

    fun verificarCampoEmail(): Boolean {
        if (binding.campoEmail.text.toString().isEmpty() == false)
            return true
        setMenssageOfErrorEmail("Hey, escreve teu email aqui!")
        return false
    }

    fun verificarCampoSenha(): Boolean {
        if (binding.campoSenha.text.toString().isEmpty() == false)
            return true
        setMenssageOfErrorPassword("Hey, escreve tua senha aqui!")
        return false
    }

    fun setMenssageOfErrorEmail(mensagem: String) {
        binding.campoEmail.setError(mensagem)
    }

    fun setMenssageOfErrorPassword(mensagem: String) {
        binding.campoSenha.setError(mensagem)
    }


}