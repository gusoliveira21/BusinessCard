package com.gusoliveira21.businesscard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gusoliveira21.businesscard.databinding.ActivityAddBusinessCardBinding
import com.gusoliveira21.businesscard.ui.MainActivity

class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        insertListener()
    }

    private fun insertListener(){
        binding.btCancel.setOnClickListener {

            val intent = Intent(this@AddBusinessCardActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}