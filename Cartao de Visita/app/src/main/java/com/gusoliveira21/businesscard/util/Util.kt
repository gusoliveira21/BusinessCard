package com.gusoliveira21.businesscard.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class Util{
companion object {
    fun statusInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        //val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return activeNetwork?.isConnectedOrConnecting == true
    }

    //TODO:Sobre os erros a baixo! Os métodos são da API 26, e o mínimo que estou usando atualmente no app é 16. Significando que celulares com api 16 podem bugar.
    fun idUsuario(): String = Base64.getEncoder().encodeToString(Firebase.auth.currentUser!!.uid.toByteArray())
    }

}



