package com.gusoliveira21.businesscard.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.gusoliveira21.businesscard.R
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType

class SlidesActivity : AppIntro() {
    //private val binding by lazy { Intro3EntradaBinding.inflate(layoutInflater) }
    //private val classeLogin: LoginActivity()
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //setContentView(binding.root)




    }




    fun configuracoesTela() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setTransformer(AppIntroPageTransformerType.Fade)
    }

    fun slidesIntroducao() {
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(
                R.layout.intro_1_hello
            )
        )

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(
                R.layout.intro_2_businesscard
            )
        )

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(
                R.layout.intro_3_entrada
            )
        )
    }

/*    fun loginAnonimo(){
        binding.buttonAnonymo.setOnClickListener {

        }
    }

    fun login(){
        binding.buttonLogin.setOnClickListener {

        }
    }

    fun registrar(){
        binding.buttonRegistrar.setOnClickListener {

        }
    }*/

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        finish()
    }
}