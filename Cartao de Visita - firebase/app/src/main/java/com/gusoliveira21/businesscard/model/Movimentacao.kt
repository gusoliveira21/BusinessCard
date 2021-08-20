package com.gusoliveira21.businesscard.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.util.util
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



    fun salvar() {
        database = Firebase.database.reference
        database.child("movimentacao")
            .child(util().idUsuario())
            .push()
            .setValue(this)
    }

}