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

class MainActivity(private val slidesactivity: SlidesActivity) : AppIntro() /*AppCompatActivity()*/ {
    //private lateinit var binding = ActivityMainBinding

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //slidesactivity.configuracoesTela()
        //slidesactivity.slidesIntroducao()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setTransformer(AppIntroPageTransformerType.Fade)


        addSlide(
            AppIntroCustomLayoutFragment.newInstance(
                R.layout.intro_1_hello
            )
        )

        // setContentView(binding.root)

        insertListener()
    }



    private fun insertListener() {
        binding.btAddCard.setOnClickListener {
            val intent = Intent(this@MainActivity, AddBusinessCardActivity::class.java)
            startActivity(intent)
        }
    }
}