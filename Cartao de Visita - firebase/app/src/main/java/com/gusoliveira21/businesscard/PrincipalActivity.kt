package com.gusoliveira21.businesscard


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroBase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.model.Movimentacao
import com.gusoliveira21.businesscard.ui.AdicionarCardActivity
import com.gusoliveira21.businesscard.ui.CardAdapter
import com.gusoliveira21.businesscard.ui.MainActivity
import java.util.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.gusoliveira21.businesscard.databinding.ActivityPrincipalBinding
import com.gusoliveira21.businesscard.util.util


class PrincipalActivity : AppIntro() {
    //Bind do layout
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }
    //private lateinit var binding: ActivityPrincipalBinding
    //Variável de autenticação e obtenção de credenciais
    private lateinit var auth: FirebaseAuth
    //variável de armazenamento/Banco de dados
    private val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private var movimentacaoRef: DatabaseReference? = null
    //Lista de objetos de cada card
    private val ListaMovimentacao = ArrayList<Movimentacao>()
    //Variável adapter usada para setar notify
    private val adapterMovimentacao: CardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recuperaMovimentacoes()
        listener()
        swipe()

        auth = Firebase.auth

    }
    fun listener() {
        binding.btAddCard.setOnClickListener {
            startActivity(Intent(this, AdicionarCardActivity::class.java))
        }
        binding.btDeslogar.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    fun swipe() {
        val itemTouch: ItemTouchHelper.Callback = object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ): Int {
                val dragFlags = ItemTouchHelper.ACTION_STATE_IDLE
                val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                return makeMovementFlags(dragFlags, swipeFlags)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.e("TAG", "-> Cartão arrastado")
                excluirMovimentacao(viewHolder)
            }
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(binding.rvCards)
    }

    fun excluirMovimentacao(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Excluir cartão da Conta!")
        alertDialog.setMessage("Realmente deseja excluir essa cartão de visita da sua conta ?")
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("Confirmar"
        ) { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Excluido!", Toast.LENGTH_SHORT).show()
            val position = viewHolder.adapterPosition
            val movimentacao = ListaMovimentacao.get(position)


            firebaseRef.child("movimentacao").child(util().idUsuario()).child(movimentacao.key!!).removeValue()
            adapterMovimentacao?.notifyItemChanged(position)
        }
        alertDialog.setNegativeButton("Cancelar"
        ) { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Cancelado!", Toast.LENGTH_SHORT).show()
            adapterMovimentacao?.notifyDataSetChanged()
        }
        val alert = alertDialog.create()
        alert.show()
        //DataFirebaseRealTime().excluirItem(viewHolder, this)//excluirItem(viewHolder: RecyclerView.ViewHolder, context: Context)
    }



    fun setupRecyclertView(context: Context) {
        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CardAdapter(ListaMovimentacao, this)
        }
    }

    fun recuperaMovimentacoes() {
        movimentacaoRef = firebaseRef.child("movimentacao").child(util().idUsuario())
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ListaMovimentacao.clear()
                dataSnapshot.children.forEach { inDataSnapshot ->
                    var movimentacao = inDataSnapshot.getValue(Movimentacao::class.java)
                    movimentacao!!.key = inDataSnapshot.key
                    ListaMovimentacao.add(movimentacao!!)
                }
                setupRecyclertView(this@PrincipalActivity)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCardAdapter", "Erro ao obter base de dados -> ",
                    databaseError.toException())
            }
        }
        movimentacaoRef!!.addValueEventListener(postListener)
        //DataFirebaseRealTime().recuperaMovimentacoes(this@PrincipalActivity)
    }
}

