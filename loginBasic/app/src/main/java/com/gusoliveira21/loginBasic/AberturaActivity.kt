package com.gusoliveira21.loginBasic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gusoliveira21.loginBasic.databinding.ActivityAberturaBinding
import com.gusoliveira21.loginBasic.databinding.ActivityCadastroBinding

class AberturaActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAberturaBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_abertura)


    }
}