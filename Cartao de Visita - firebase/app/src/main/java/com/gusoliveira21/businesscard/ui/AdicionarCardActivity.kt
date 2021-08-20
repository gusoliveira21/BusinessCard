package com.gusoliveira21.businesscard.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.gusoliveira21.businesscard.databinding.ActivityAdicionarCardBinding
import com.gusoliveira21.businesscard.model.Movimentacao

class AdicionarCardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAdicionarCardBinding.inflate(layoutInflater) }
    val movimentacoes = Movimentacao("", "", "", "", "")

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
        if ((hasFieldEmpty()== false) && (colorFieldIsEmpty() == false)) {
            Toast.makeText(this, "Dentro", Toast.LENGTH_LONG).show()
            movimentacoes.nome = binding.campoNome.text.toString()
            movimentacoes.telefone = binding.campoTelefone.text.toString()
            movimentacoes.email = binding.campoEmail.text.toString()
            movimentacoes.empresa = binding.campoEmpresa.text.toString()
            movimentacoes.cor = selectColorRadioButton()
            movimentacoes.salvar()
            finish()
        } else Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
    }


    fun selectColorRadioButton(): String {
        when{
            binding.radioButton2.isChecked -> return "#000000"
            binding.radioButton3.isChecked -> return "#EF5350"
            binding.radioButton4.isChecked -> return "#EC407A"
            binding.radioButton5.isChecked -> return "#AB47BC"
            binding.radioButton6.isChecked -> return "#7E57C2"
            binding.radioButton7.isChecked -> return "#5C6BC0"
            binding.radioButton8.isChecked -> return "#42A5F5"
            binding.radioButton9.isChecked -> return "#29B6F6"
            binding.radioButton10.isChecked -> return "#26C6DA"
            binding.radioButton11.isChecked -> return "#26A69A"
            binding.radioButton12.isChecked -> return "#66BB6A"
            binding.radioButton13.isChecked -> return "#9CCC65"
            binding.radioButton14.isChecked -> return "#D4E157"
            binding.radioButton15.isChecked -> return "#FFEE58"
            binding.radioButton16.isChecked -> return "#FFCA28"
            binding.radioButton17.isChecked -> return "#FFA726"
            binding.radioButton18.isChecked -> return "#FF7043"
            binding.radioButton19.isChecked -> return "#8D6E63"
            binding.radioButton20.isChecked -> return "#BDBDBD"
            binding.radioButton21.isChecked -> return "#78909C"
        }
        return "erro"
    }

    private fun hasFieldEmpty(): Boolean {
        return (binding.campoNome.text.toString().isEmpty() ||
                binding.campoEmpresa.text.toString().isEmpty() ||
                binding.campoEmail.text.toString().isEmpty() ||
                binding.campoTelefone.text.toString().isEmpty())
    }
    private fun colorFieldIsEmpty(): Boolean {
        return ((binding.radioGroupOne.checkedRadioButtonId == -1 &&
                binding.radioGroupTwo.checkedRadioButtonId == -1 &&
                binding.radioGroupThree.checkedRadioButtonId == -1))
    }

    fun radioGroupOne(view: View) {
        if (binding.radioGroupTwo.checkedRadioButtonId != -1)
            binding.radioGroupTwo.clearCheck()
        if (binding.radioGroupThree.checkedRadioButtonId != -1)
            binding.radioGroupThree.clearCheck()
    }
    fun radioGroupTwo(view: View) {
        if (binding.radioGroupOne.checkedRadioButtonId != -1)
            binding.radioGroupOne.clearCheck()
        if (binding.radioGroupThree.checkedRadioButtonId != -1)
            binding.radioGroupThree.clearCheck()
    }
    fun radioGroupThree(view: View) {
        if (binding.radioGroupOne.checkedRadioButtonId != -1)
            binding.radioGroupOne.clearCheck()
        if (binding.radioGroupTwo.checkedRadioButtonId != -1)
            binding.radioGroupTwo.clearCheck()
    }

}