package com.gusoliveira21.businesscard.ui

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.gusoliveira21.businesscard.databinding.ActivityAdicionarCardBinding
import com.gusoliveira21.businesscard.databinding.ActivityColorBinding
import com.gusoliveira21.businesscard.databinding.ItemBusinessCardBinding


class ActivityColor : AppCompatActivity() {
    private val binding by lazy { ActivityColorBinding.inflate(layoutInflater) }
    private val bindingCard by lazy { ActivityAdicionarCardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("Escolha uma cor para o cartão:")

    }

    fun confirm(view:View){
         if (checkIfHasColorSelected()) {

            finish()

        } else
            Log.e("TAG", "Não tem cor!")
    }


    fun selectColorRadioButton(): String {
        when{
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
            binding.radioButton2.isChecked -> return "#000000"
        }
        return "erro"
    }

    private fun checkIfHasColorSelected(): Boolean {
        return (!(binding.radioGroupOne.checkedRadioButtonId == -1 &&
                binding.radioGroupTwo.checkedRadioButtonId == -1 &&
                binding.radioGroupThree.checkedRadioButtonId == -1))
    }

    fun cancel(view:View){
        finish()
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

