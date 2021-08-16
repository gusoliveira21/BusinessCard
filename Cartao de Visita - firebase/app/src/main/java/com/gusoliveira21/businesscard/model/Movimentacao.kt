package com.gusoliveira21.businesscard.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

@IgnoreExtraProperties
data class Movimentacao(
    var nome: String? = null,
    var empresa: String? = null,
    var telefone: String? = null,
    var email: String? = null,
    var cor: String? = null,
    var key: String? = null,
) {
    private lateinit var database: DatabaseReference



    @RequiresApi(Build.VERSION_CODES.O)
    fun salvar() {
        database = Firebase.database.reference
        var idUsuario = Base64.getEncoder().encodeToString(Firebase.auth.currentUser!!.email!!.toByteArray())
        database.child("movimentacao")
            .child(idUsuario)
            .push()
            .setValue(this)
    }

}