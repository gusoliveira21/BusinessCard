package com.gusoliveira21.businesscard.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.gusoliveira21.businesscard.R
import com.gusoliveira21.businesscard.databinding.ActivityMainBinding

class MainActivity : AppIntro() /*AppCompatActivity()*/ {
    //private lateinit var binding = ActivityMainBinding
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Remove a barra de status que contém a hora, data, notificações
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setTransformer(AppIntroPageTransformerType.Fade)

/*
   titleTypefaceFontRes = R.font.opensans_regular,
    descriptionTypefaceFontRes = R.font.opensans_regular
* */

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

        // setContentView(binding.root)

        insertListener()
    }

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


    private fun insertListener() {
        binding.btAddCard.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }
    }
}