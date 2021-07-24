package com.gusoliveira21.businesscard.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.Intro3AcessoBinding

//import com.gusoliveira21.businesscard.databinding.ActivityMainBinding
//import com.gusoliveira21.businesscard.databinding.Intro3AcessoBinding


class MainActivity() : AppIntro() {

    //private val mainBinding by lazy { Intro3AcessoBinding.inflate(layoutInflater) }
    //private val acessoBinding by lazy { Intro3AcessoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configuracoesSlide()
        slideIntroducao()

    }



    fun configuracoesSlide() {
        setImmersiveMode()
        isSkipButtonEnabled = false
        isButtonsEnabled = false
        setTransformer(AppIntroPageTransformerType.Fade)
    }

    fun slideIntroducao() {
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_1_hello))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_2_businesscard))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_3_acesso))
    }


    private fun verificarUsuarioLogado() {

    }

    fun btLogin(view: View) {
        startActivity(Intent(this, LoginActivity::class.java))
        Toast.makeText(this, "login", Toast.LENGTH_SHORT).show()
    }

    fun btRegistrar(view: View) {
        startActivity(Intent(this, CadastroActivity::class.java))
        Toast.makeText(this, "registrar", Toast.LENGTH_SHORT).show()
    }

    fun btUseAnonimamente(view: View) {
        startActivity(Intent(this, AnonimoActivity::class.java))
        Toast.makeText(this, "anonimo", Toast.LENGTH_SHORT).show()
    }


}