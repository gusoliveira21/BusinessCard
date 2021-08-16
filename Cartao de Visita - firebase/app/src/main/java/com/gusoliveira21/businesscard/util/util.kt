package com.gusoliveira21.businesscard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.LayoutInflater
import android.widget.Toast
import com.gusoliveira21.businesscard.databinding.ItemBusinessCardBinding

class util {


    fun opcoesErro(errorValue: String) {
        when {
            /*errorValue.contains("email address is already in use") -> binding.campoEmailLogin.setError("O endereço de email já está sendo usado em outra conta!")
            errorValue.contains("password is invalid") -> setMessageOfErrorPassword("Digite uma senha mais forte! \n A cima de 6 dígitos!")
            else -> Toast.makeText(this, "Houve um erro ao cadastrar usuário!", Toast.LENGTH_SHORT).show()*/
        }
    }












    fun statusInternet(context: Context):Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }

}