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
import com.example.retromarket.data.model.LoginResponse
import com.example.retromarket.data.model.User
import com.example.retromarket.service.SessionManager
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
                val api = RetrofitClient.instance
                val call = api.login(user)
                call.enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        if (response.isSuccessful) {
                            val authResponse = response.body()
                            if (authResponse != null) {

                                // Sauvegarde du token
                                val token = authResponse.token
                                if (token != null) {
                                    SessionManager.saveAuthToken(this@LoginActivity, token)
                                }
                                val userId = authResponse.user.id
                                if (userId !=null){
                                    SessionManager.saveUserId(this@LoginActivity, userId)
                                }
                                val loggeninUser = authResponse.user
                                SessionManager.saveUser(this@LoginActivity, loggeninUser)

                                Toast.makeText(this@LoginActivity, "Bienvenue ${authResponse.user.username}", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finish()
                            }
                        } else {
                            Toast.makeText(this@LoginActivity, "Erreur : ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Erreur réseau : ${t.message}", Toast.LENGTH_SHORT).show()
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