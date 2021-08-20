package com.gusoliveira21.businesscard.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.ItemBusinessCardBinding
import com.gusoliveira21.businesscard.model.Movimentacao
import java.util.*

class CardAdapter() : RecyclerView.Adapter<CardViewHolder>(){

    var movimentacoes = ArrayList<Movimentacao>()

    constructor(arrayMovimentacoes: ArrayList<Movimentacao>, principalActivity: RecyclerView):this(){
        this.movimentacoes = arrayMovimentacoes
    }

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessCardBinding.inflate(inflater,parent,false)
        return CardViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val (nome, empresa, telefone, email, cor, key) = movimentacoes[position]
        holder.nome.text = nome!!.uppercase()
        holder.empresa.text = empresa
        holder.telefone.text = telefone
        holder.email.text = email
        holder.cor.setCardBackgroundColor(Color.parseColor(cor))
        holder.backgroungImagem.setBackgroundResource(R.drawable.backgroungcardmarcadagua)
        holder.viewEmail.text = "Email:"
        holder.viewTelefone.text = "Telefone:"
    }




    override fun getItemCount(): Int    {
        return movimentacoes.size
    }

}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nome: TextView = itemView.findViewById(R.id.tv_nome)
    val empresa: TextView = itemView.findViewById(R.id.tv_empresa)
    val telefone: TextView = itemView.findViewById(R.id.tv_telefone)
    val email: TextView = itemView.findViewById(R.id.tv_email)
    val cor = itemView.findViewById<CardView>(R.id.mcv_content)
    val backgroungImagem = itemView.findViewById<ImageView>(R.id.imageBackgroung)
    val viewTelefone = itemView.findViewById<TextView>(R.id.tv_tel)
    val viewEmail = itemView.findViewById<TextView>(R.id.tv_ema)
}