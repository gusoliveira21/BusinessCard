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



class CardAdapter():
    RecyclerView.Adapter<CardViewHolder>() {
    var movimentacoes = ArrayList<MovimentacaoFirebase>()
    var listnerShare: (View) -> Unit = {}

    constructor(arrayMovimentacoes: ArrayList<MovimentacaoFirebase>, principalActivity: PrincipalActivity,):this(){
        this.movimentacoes = arrayMovimentacoes
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBusinessCardBinding.inflate(inflater, parent, false)
        return CardViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val (nome, empresa, telefone, email, cor, key) = movimentacoes[position]

        holder.card.setOnClickListener {
            //listnerShare(it)
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

class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val card: CardView = itemView.findViewById(R.id.cardview)
    val nome: TextView = itemView.findViewById(R.id.tv_nome)
    val email: TextView = itemView.findViewById(R.id.tv_email)
    val empresa: TextView = itemView.findViewById(R.id.tv_empresa)
    val telefone: TextView = itemView.findViewById(R.id.tv_telefone)
    val viewEmail: TextView = itemView.findViewById(R.id.tv_ema)
    val viewTelefone: TextView = itemView.findViewById(R.id.tv_tel)
    val backgroungImagem: ImageView = itemView.findViewById(R.id.imageBackgroung)
}