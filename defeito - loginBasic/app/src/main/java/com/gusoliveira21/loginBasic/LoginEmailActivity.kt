package com.gusoliveira21.loginBasic

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.gusoliveira21.loginBasic.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLoginEmailBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


    }

    fun login(view: View) {
        if (verificarCampoSenha() && verificarCampoEmail()) {
            auth.signInWithEmailAndPassword(
                binding.etEmailLogin.text.toString(),
                binding.etSenhaLogin.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Acessando!", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    startActivity(Intent(this, PrincipalActivity::class.java))
                    finish()
                } else {
                    Log.i(TAG, "mensagem: ${task.exception}")
                    opcoesErro(task.exception.toString())
                }
            }
        }


    }

    fun opcoesErro(mensagemErro: String) {
        if(mensagemErro.contains("badly formatted")) binding.etEmailLogin.setError("Forneça corretamente o email!")
        else if(mensagemErro.contains("user may have been deleted")) binding.etEmailLogin.setError("Email não cadastrado!")
        else if(mensagemErro.contains("password is invalid")) binding.etEmailLogin.setError("Senha incorreta!")
        else Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
    }

    fun verificarCampoEmail(): Boolean {
        if (binding.etEmailLogin.text.isEmpty() == false)
            return true
        setMenssageOfErrorEmail("Hey, escreve teu email aqui!")
        return false
    }

    fun verificarCampoSenha(): Boolean {
        if (binding.etSenhaLogin.text.isEmpty() == false)
            return true
        setMenssageOfErrorPassword("Hey, escreve tua senha aqui!")
        return false
    }

    fun setMenssageOfErrorEmail(mensagem: String) {
        binding.etEmailLogin.setError(mensagem)
    }

    fun setMenssageOfErrorPassword(mensagem: String) {
        binding.etSenhaLogin.setError(mensagem)
    }

    fun recuperarSenha(view: View) {
        if(binding.etEmailLogin.text.isEmpty() == false){
            enviaEmail()
        }else{
            verificarCampoEmail()
        }
    }

    fun enviaEmail(){
    auth.sendPasswordResetEmail(binding.etEmailLogin.text.toString()).addOnSuccessListener {
        Toast.makeText(this, "Um email foi enviado para sua conta!", Toast.LENGTH_SHORT)
            .show()
    }
        .addOnFailureListener { e ->
            opcoesErro(e.toString())
            deixaCampoSenhaELoginInvisive()

        }
}

    fun deixaCampoSenhaELoginInvisive(){
        binding.btLoginEntrarNaConta.visibility = View.INVISIBLE
        binding.etSenhaLogin.visibility = View.INVISIBLE
    }




}