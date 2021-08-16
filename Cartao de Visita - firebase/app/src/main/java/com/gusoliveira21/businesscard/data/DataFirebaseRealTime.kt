package com.gusoliveira21.businesscard.data

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.model.Movimentacao
import java.util.*

open class DataFirebaseRealTime {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.O)
    fun dataBase() {
        database = Firebase.database.reference

        var idUsuario =
            Base64.getEncoder().encodeToString(Firebase.auth.currentUser!!.email!!.toByteArray())
        var databaseFirebase = database.child("movimentacao").child(idUsuario)


        databaseFirebase.addValueEventListener(valueEventlistener())
    }


    fun valueEventlistener(): ValueEventListener {
        val postListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { dataSnapshot ->
                    var cardFirebase = dataSnapshot.getValue<Movimentacao>()




                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCardAdapter",
                    "Erro ao obter base de dados -> ",
                    databaseError.toException())
            }

        }
        return postListener
    }

}