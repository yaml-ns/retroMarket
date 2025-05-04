package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retromarket.BaseActivity
import com.example.retromarket.R

class ConfirmationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmation)

        val connexionBtn : Button = findViewById(R.id.connexionBtn)
        connexionBtn.setOnClickListener{
            val intent = Intent(this@ConfirmationActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}