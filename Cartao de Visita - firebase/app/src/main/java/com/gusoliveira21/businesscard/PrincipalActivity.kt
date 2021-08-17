package com.gusoliveira21.businesscard


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.appintro.AppIntro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.databinding.ActivityPrincipalBinding
import com.gusoliveira21.businesscard.model.Movimentacao
import com.gusoliveira21.businesscard.ui.AdicionarCardActivity
import com.gusoliveira21.businesscard.ui.CardAdapter
import com.gusoliveira21.businesscard.ui.MainActivity
import java.util.*

class PrincipalActivity : AppIntro() {
    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }

    private val adapterCard = CardAdapter()
    private var movimentacaoRef: DatabaseReference? = null
    private val firebaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    private val ListaMovimentacao = ArrayList<Movimentacao>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //setupRecyclertView(this)
        recuperaMovimentacoes()

        auth = Firebase.auth
        listener()
    }


    fun listener() {
        binding.btAddCard.setOnClickListener {
            startActivity(Intent(this,
                AdicionarCardActivity::class.java))
        }

        binding.btDeslogar.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setupRecyclertView(context: Context) {
        binding.rvCards.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CardAdapter(ListaMovimentacao, this)
        }
    }

    private fun recuperaMovimentacoes() {
        var idUsuario =
            Base64.getEncoder().encodeToString(Firebase.auth.currentUser!!.email!!.toByteArray())
        movimentacaoRef = firebaseRef.child("movimentacao").child(idUsuario)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                ListaMovimentacao.clear()

                dataSnapshot.children.forEach{ inDataSnapshot ->
                    var movimentacao = inDataSnapshot.getValue(Movimentacao::class.java)
                    movimentacao!!.key = inDataSnapshot.key
                    ListaMovimentacao.add(movimentacao)

                    val nome = inDataSnapshot.child("nome").value.toString()
                    val empresa = inDataSnapshot.child("empresa").value.toString()
                    val telefone = inDataSnapshot.child("telefone").value.toString()
                    val email = inDataSnapshot.child("email").value.toString()

                    Log.e("RecuperaMovimentacoes", "Key -> ${inDataSnapshot.key}")
                    Log.e("RecuperaMovimentacoes", "-> ${nome}")
                    Log.e("RecuperaMovimentacoes", "-> ${empresa}")
                    Log.e("RecuperaMovimentacoes", "-> ${telefone}")
                    Log.e("RecuperaMovimentacoes", "-> ${email}")
                    Log.e("RecuperaMovimentacoes", "--------------------------------------------")
                }
                setupRecyclertView(this@PrincipalActivity)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCardAdapter",
                    "Erro ao obter base de dados -> ",
                    databaseError.toException())
            }
        }
        movimentacaoRef!!.addValueEventListener(postListener)
    }

}

