package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.model.User
import com.example.retromarket.service.SessionManager

class MyProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val etFirstName = findViewById<EditText>(R.id.etFirstName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val btnUpdateProfile = findViewById<Button>(R.id.btnUpdateProfile)

        // Récupérer les informations de l'utilisateur et les afficher dans les champs de formulaire
        val user = SessionManager.fetchUser(this)
        if (user != null) {
            etFirstName.setText(user.prenom)
            etLastName.setText(user.nom)
            etEmail.setText(user.email)
        }

        btnUpdateProfile.setOnClickListener {
            val firstName = etFirstName.text.toString()
            val lastName = etLastName.text.toString()
            val email = etEmail.text.toString()

            if (firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()) {
                // Mettre à jour les informations de l'utilisateur
                val updatedUser = user?.copy(prenom = firstName, nom = lastName, email = email)
                if (updatedUser != null) {
                    SessionManager.saveUser(this, updatedUser)
                    Toast.makeText(this, "Profil mis à jour avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
