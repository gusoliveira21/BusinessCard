package com.gusoliveira21.loginBasic

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.*
import com.gusoliveira21.loginBasic.databinding.ActivityCadastroBinding
import com.gusoliveira21.loginBasic.util.Util

class CadastroActivity : AppCompatActivity() {
    val binding by lazy { ActivityCadastroBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()


    }

    fun cadastrarUsuario(view: View) {

        if (verificaSenhas() && verificaSeEmailEEmpty()) {
            auth.createUserWithEmailAndPassword(
                binding.etEmailCadastro.text.toString(),
                binding.etSenhaUmCadastro.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(
                            this,
                            "Cadastro realizado com sucesso!",
                            Toast.LENGTH_SHORT)
                            .show()
                        val user = auth.currentUser
                        startActivity(Intent(this, PrincipalActivity::class.java))
                        finish()

                    } else {
                        Log.i(TAG, "mensagem: ${task.exception}")
                        //TODO: CÓDIGO USADO NESSA CLASSE
                        //opcoesErro(task.exception.toString())
                        //TODO: Como posso passar a binding entre classes?
                        Util().opcoesErro(task.exception.toString(),this, findViewById(R.layout.activity_cadastro))


                    }
                }
        }
    }

    fun opcoesErro(errorValue: String) {
        when (errorValue) {
            "com.google.firebase.auth.FirebaseAuthUserCollisionException: The email address is already in use by another account." -> binding.etEmailCadastro.setError(
                "O endereço de email já está sendo usado em outra conta!")
            "com.google.firebase.auth.FirebaseAuthWeakPasswordException: The given password is invalid. [ Password should be at least 6 characters ]" -> setMessageOfErrorPassword(
                "Digite uma senha mais forte! \n A cima de 6 dígitos!")
            else -> Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()
        }
    }

    fun verificaSenhas(): Boolean {
        if ((binding.etSenhaUmCadastro.text.toString().isEmpty() or binding.etSenhaDoisCadastro.text.toString()
                .isEmpty()) == false
        )
            if (binding.etSenhaUmCadastro.text.toString()
                    .equals(binding.etSenhaDoisCadastro.text.toString())
            )
                return true
        setMessageOfErrorPassword("Senhas não conferem!")
        return false
    }

    fun verificaSeEmailEEmpty(): Boolean {
        if (binding.etEmailCadastro.text!!.isEmpty() == false)
            return true
        setMessageOfErrorEmail()
        return false
    }

    fun setMessageOfErrorEmail() {
        binding.etEmailCadastro.setError("Digite um email!")
    }

    fun setMessageOfErrorPassword(mensagem: String) {
        binding.etSenhaUmCadastro.setError(mensagem)
        binding.etSenhaDoisCadastro.setError(mensagem)
    }

    fun cancelarCadastro(view: View) {
        finish()
    }
}