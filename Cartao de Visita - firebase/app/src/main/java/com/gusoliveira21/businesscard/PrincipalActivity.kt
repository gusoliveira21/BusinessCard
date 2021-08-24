package com.gusoliveira21.businesscard


import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.businesscard.util.Image
import com.github.appintro.AppIntro
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.gusoliveira21.businesscard.model.MovimentacaoFirebase
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
    private val ListaDeCartoes = ArrayList<MovimentacaoFirebase>()

    //adapter para utilizar compartilhamento
    private val adapter by lazy { CardAdapter(ListaDeCartoes, this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpPermissions()
        listener()
        adicionaCardDoFirebaseNalistaDeCartoes()
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
        binding.swiperefresh.setOnRefreshListener {
            //TODO: Colocar animação durante carregamento de alse
        }

        CardAdapter(ListaDeCartoes, this).listnerShare = { card ->
            Image.share(this, card)
        }

    }



    fun setupRecyclertView(context: Context) {
        binding.rvCards.layoutManager = LinearLayoutManager(context)
        binding.rvCards.adapter = adapter
        //binding.rvCards.adapter = CardAdapter(ListaDeCartoes, this)
    }


    private fun setUpPermissions() {
        // write permission to access the storage
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
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
                return false //recurso pip
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == 32) excluirCard(viewHolder)
                else {
                    editarCard(viewHolder)
                }
            }
        }
        ItemTouchHelper(itemTouch).attachToRecyclerView(binding.rvCards)
    }

    fun editarCard(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Editar cartão!")
        alertDialog.setMessage("Realmente deseja editar este cartão ?")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Confirmar"
        ) { dialog, which ->
            val cartaoQueFoiArrastado = ListaDeCartoes.get(viewHolder.adapterPosition)
            val intent = Intent(this, AdicionarCardActivity::class.java).apply {
                putExtra("telefone", cartaoQueFoiArrastado.telefone)
                putExtra("empresa", cartaoQueFoiArrastado.empresa)
                putExtra("email", cartaoQueFoiArrastado.email)
                putExtra("nome", cartaoQueFoiArrastado.nome)
                putExtra("cor", cartaoQueFoiArrastado.cor)
                putExtra("key", cartaoQueFoiArrastado.key)
            }
            startActivity(intent)
            binding.rvCards.adapter?.notifyItemChanged(viewHolder.adapterPosition)
        }
        alertDialog.setNegativeButton("Cancelar"
        ) { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Cancelado!", Toast.LENGTH_SHORT).show()
            binding.rvCards.adapter?.notifyItemChanged(viewHolder.adapterPosition)
        }
        val alert = alertDialog.create()
        alert.show()
    }

    fun excluirCard(viewHolder: RecyclerView.ViewHolder) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Excluir cartão da Conta!")
        alertDialog.setMessage("Realmente deseja excluir essa cartão de visita da sua conta ?")
        alertDialog.setCancelable(false)

        alertDialog.setPositiveButton("Confirmar"
        ) { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Excluido!", Toast.LENGTH_SHORT).show()
            val cartaoEscolhido = ListaDeCartoes.get(viewHolder.adapterPosition)
            cartaoEscolhido.excluir()
            binding.rvCards.adapter?.notifyItemChanged(viewHolder.adapterPosition)
        }
        alertDialog.setNegativeButton("Cancelar"
        ) { dialog, which ->
            Toast.makeText(this@PrincipalActivity, "Cancelado!", Toast.LENGTH_SHORT).show()
            binding.rvCards.adapter?.notifyItemChanged(viewHolder.adapterPosition)
        }
        val alert = alertDialog.create()
        alert.show()
    }

    fun adicionaCardDoFirebaseNalistaDeCartoes() {
        movimentacaoRef = firebaseRef.child("movimentacao").child(util().idUsuario())
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                ListaDeCartoes.clear()
                dataSnapshot.children.forEach { inDataSnapshot ->
                    var movimentacao = inDataSnapshot.getValue(MovimentacaoFirebase::class.java)
                    movimentacao!!.key = inDataSnapshot.key
                    ListaDeCartoes.add(movimentacao!!)
                }
                setupRecyclertView(this@PrincipalActivity)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("FirebaseCardAdapter", "Erro ao obter base de dados -> ",
                    databaseError.toException())
            }
        }
        movimentacaoRef!!.addValueEventListener(postListener)
    }
}



