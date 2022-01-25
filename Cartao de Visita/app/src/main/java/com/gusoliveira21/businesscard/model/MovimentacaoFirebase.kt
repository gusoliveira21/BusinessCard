package com.gusoliveira21.businesscard.model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.util.Util

@IgnoreExtraProperties
data class MovimentacaoFirebase(
    var nome: String? = null,
    var empresa: String? = null,
    var telefone: String? = null,
    var email: String? = null,
    var cor: String? = null,
    var key: String? = null,
) {

        private var database: DatabaseReference = Firebase.database.reference

        private fun toMap(): Map<String?, Any?> {
            return mapOf(
                "nome" to nome,
                "empresa" to empresa,
                "telefone" to telefone,
                "email" to email,
                "cor" to cor,
            )
        }

        fun salvar() {
            database.child("movimentacao")
                .child(Util.idUsuario())
                .push()
                .setValue(this)
        }

        fun editar() {
            database.child("movimentacao")
                .child(Util.idUsuario())
                .child(key.toString())
                .updateChildren(toMap())
        }

        fun excluir() {
            database.child("movimentacao")
                .child(Util.idUsuario())
                .child(key!!)
                .removeValue()
        }
}