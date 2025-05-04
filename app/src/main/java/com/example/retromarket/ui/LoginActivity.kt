package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.retromarket.BaseActivity
import com.example.retromarket.MainActivity
import com.example.retromarket.R
import com.example.retromarket.databinding.ActivityLoginBinding
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.AuthResponse
import com.example.retromarket.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val registerLink = findViewById<TextView>(R.id.tvRegister)
        val forgotPasswordLink = findViewById<TextView>(R.id.tvForgotPassword)
        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this@LoginActivity, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()

            }else{
                val user = User(null,email, password,null,null,null,null,null)

                RetrofitClient.instance.login(user).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        val res = response.body()
                        if (res?.message == "Login successful" && res.token != null) {
                            Toast.makeText(this@LoginActivity, "Connexion réussie", Toast.LENGTH_SHORT).show()
                            getSharedPreferences("retro_market", MODE_PRIVATE).edit().clear().apply()
                            getSharedPreferences("retro_market", MODE_PRIVATE).edit().putString("token", res.token).apply()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@LoginActivity, res?.message ?: "Erreur de connexion", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Erreur serveur: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }


        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgotPasswordLink.setOnClickListener {
            val email = findViewById<TextView>(R.id.etEmail).text.toString()
            if (email.isNotEmpty()) {
                RetrofitClient.instance.forgotPassword(mapOf("email" to email)).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Erreur: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Veuillez entrer votre email", Toast.LENGTH_SHORT).show()
            }
        }
    }
}