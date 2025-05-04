package com.example.retromarket.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.databinding.ActivityRegisterBinding
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.AuthResponse
import com.example.retromarket.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)
        val registerBtn = findViewById<Button>(R.id.btnRegister)

        val spinner = findViewById<Spinner>(R.id.etGenre)
        val items = listOf("Homme", "Femme", "Autre")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        registerBtn.setOnClickListener {

            val nom = findViewById<TextView>(R.id.etName).text.toString()
            val prenom = findViewById<TextView>(R.id.etFirstname).text.toString()
            val username = findViewById<TextView>(R.id.etUsername).text.toString()
            val genre = spinner.selectedItem.toString()
            val email = findViewById<TextView>(R.id.etEmail).text.toString()
            val password = findViewById<TextView>(R.id.etPassword).text.toString()
            val repeatPassword = findViewById<TextView>(R.id.etRepeatPassword).text.toString()

            if (nom.isBlank() || prenom.isBlank() || username.isBlank() || email.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
                Toast.makeText(this@RegisterActivity, "Erreur : Veuillez remplir tous les champs svp.", Toast.LENGTH_SHORT).show()
            }else if(password != repeatPassword){
                Toast.makeText(this@RegisterActivity, "Erreur : Les deux mot de passe ne sont pas identiques", Toast.LENGTH_SHORT).show()
            }else{
                val user = User(null,email, password,username,nom,prenom,genre,null)
                RetrofitClient.instance.register(user).enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                        val res = response.body()
                        if (res?.message == "Utilisateur créé avec succès") {
                            val intent = Intent(this@RegisterActivity, ConfirmationActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@RegisterActivity, res?.message ?: "Erreur d'inscription", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Erreur: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }


        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}
