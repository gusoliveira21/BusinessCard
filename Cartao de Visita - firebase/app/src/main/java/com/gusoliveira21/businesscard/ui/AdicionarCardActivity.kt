package com.gusoliveira21.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gusoliveira21.businesscard.databinding.ActivityAdicionarCardBinding
import com.gusoliveira21.businesscard.model.Movimentacao

class AdicionarCardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAdicionarCardBinding.inflate(layoutInflater) }
    val movimentacoes =  Movimentacao("","","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        listeners()
    }

    fun listeners() {
        binding.btCancel.setOnClickListener { finish() }
        binding.btConfirm.setOnClickListener { concluir() }
    }

    fun concluir() {
         if(temCamposVazios() == false) {
             movimentacoes.nome = binding.campoNome.text.toString()
             movimentacoes.telefone = binding.campoTelefone.text.toString()
             movimentacoes.email = binding.campoEmail.text.toString()
             movimentacoes.empresa = binding.campoEmpresa.text.toString()
             movimentacoes.cor = binding.campoCor.text.toString()
             movimentacoes.salvar()
             finish()
         }else Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()

    }

    fun temCamposVazios():Boolean{
        if(binding.campoNome.text.toString().isEmpty() ||
            binding.campoEmpresa.text.toString().isEmpty()||
            binding.campoEmail.text.toString().isEmpty()||
            binding.campoTelefone.text.toString().isEmpty()||
            binding.campoCor.text.toString().isEmpty())
                return true
        return false
    }
}