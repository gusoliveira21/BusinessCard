package com.gusoliveira21.businesscard.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.PrincipalActivity

import java.util.*

class util() {


    /*fun opcoesErro(errorValue: String) {
        when {

            errorValue.contains("email address is already in use") -> bind().campoEmail.setError(
                "O endereço de email já está sendo usado em outra conta!")
            errorValue.contains("password is invalid") -> setMessageOfErrorPassword("Digite uma senha mais forte! \n A cima de 6 dígitos!")
            else -> Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT)
                .show() * /
        }
    }*/


    fun statusInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
    fun idUsuario(): String {
        var idUsuario =
            Base64.getEncoder().encodeToString(Firebase.auth.currentUser!!.email!!.toByteArray())
        return idUsuario
    }




}


