package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retromarket.databinding.ActivityRegisterBinding
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.AuthResponse
import com.example.retromarket.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            val user = User(null,email, password,null,null,null,null)
            RetrofitClient.instance.register(user).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    val res = response.body()
                    if (res?.message == "true") {
                        Toast.makeText(this@RegisterActivity, "Inscription réussie", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, res?.message ?: "Erreur d'inscription", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Erreur: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.tvLogin.setOnClickListener {
            finish()
        }
    }
}
