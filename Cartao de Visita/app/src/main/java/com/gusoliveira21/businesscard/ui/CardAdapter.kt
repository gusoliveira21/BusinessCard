package com.gusoliveira21.businesscard.ui

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.businesscard.util.Image
import com.gusoliveira21.businesscard.PrincipalActivity
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.ItemBusinessCardBinding
import com.gusoliveira21.businesscard.model.MovimentacaoFirebase
import java.util.*



class CardAdapter(): RecyclerView.Adapter<CardViewHolder>() {
    var movimentacoes = ArrayList<MovimentacaoFirebase>()
    var listnerShare: (View) -> Unit = {}

    constructor(arrayMovimentacoes: ArrayList<MovimentacaoFirebase>, principalActivity: PrincipalActivity,):this(){
        this.movimentacoes = arrayMovimentacoes
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemBusinessCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val (nome, empresa, telefone, email, cor, key) = movimentacoes[position]

        holder.card.setOnClickListener {
              Image.share(holder.card.context, it)
        }

        holder.card.setCardBackgroundColor(Color.parseColor(cor))
        holder.nome.text = nome!!.uppercase()
        holder.email.text = email
        holder.empresa.text = empresa
        holder.telefone.text = telefone
        holder.viewEmail.text = "Email:"
        holder.viewTelefone.text = "Telefone:"
        holder.backgroungImagem.setBackgroundResource(R.drawable.backgroungcardmarcadagua)
    }

    override fun getItemCount(): Int {
        return movimentacoes.size
    }
}

class CardViewHolder(private val binding: ItemBusinessCardBinding): RecyclerView.ViewHolder(binding.root) {
    val card = binding.cardview
    val nome = binding.tvNome
    val email = binding.tvEmail
    val empresa = binding.tvEmpresa
    val telefone = binding.tvTelefone
    val viewEmail = binding.tvEma
    val viewTelefone = binding.tvTel
    val backgroungImagem = binding.imageBackgroung
}