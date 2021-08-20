package com.gusoliveira21.businesscard.data

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.model.Movimentacao
import com.gusoliveira21.businesscard.ui.CardAdapter
import com.gusoliveira21.businesscard.util.util
import java.util.*

class DataFirebaseRealTime {
    //Variável de autenticação e obtenção de credenciais
    private lateinit var auth: FirebaseAuth
    //variável de armazenamento/Banco de dados
    private val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private var movimentacaoRef: DatabaseReference? = null
    //Lista de objetos de cada card
    private val ListaMovimentacao = ArrayList<Movimentacao>()
    //Variável adapter usada para setar notify
    private val adapterMovimentacao: CardAdapter? = null

    fun excluirItem(viewHolder: RecyclerView.ViewHolder, context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Excluir cartão da Conta!")
        alertDialog.setMessage("Realmente deseja excluir essa cartão de visita da sua conta ?")
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("Confirmar"
        ) { dialog, which ->
            Toast.makeText(context, "Excluido!", Toast.LENGTH_SHORT).show()
            val position = viewHolder.adapterPosition
            val movimentacao = ListaMovimentacao.get(position)


            firebaseRef.child("movimentacao").child(util().idUsuario()).child(movimentacao.key!!).removeValue()
            adapterMovimentacao?.notifyItemChanged(position)
        }
        alertDialog.setNegativeButton("Cancelar"
        ) { dialog, which ->
            Toast.makeText(context, "Cancelado!", Toast.LENGTH_SHORT).show()
            adapterMovimentacao?.notifyDataSetChanged()
        }
        val alert = alertDialog.create()
        alert.show()
    }


    fun recuperaMovimentacoes(context: Context) {
        movimentacaoRef = firebaseRef.child("movimentacao").child(util().idUsuario())
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ListaMovimentacao.clear()
                dataSnapshot.children.forEach { inDataSnapshot ->
                    var movimentacao = inDataSnapshot.getValue(Movimentacao::class.java)
                    movimentacao!!.key = inDataSnapshot.key
                    ListaMovimentacao.add(movimentacao!!)
                }
                PrincipalActivity().setupRecyclertView(context)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCardAdapter", "Erro ao obter base de dados -> ",
                    databaseError.toException())
            }
        }
        movimentacaoRef!!.addValueEventListener(postListener)
    }

}