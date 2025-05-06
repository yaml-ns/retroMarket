package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.Product
import com.example.retromarket.data.model.ProductResponse
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val priceEditText: EditText = findViewById(R.id.priceEditText)
        val categoryEditText: EditText = findViewById(R.id.categoryEditText)
        val imageEditText: EditText = findViewById(R.id.imageEditText)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString().toDouble()
            val category = categoryEditText.text.toString()
            val image = imageEditText.text.toString()
            val userId = SessionManager.fetchUserId(this)

            val product = Product(0, title, description, price, category, image, userId)
            createProduct(product)
        }
    }

    private fun createProduct(product: Product) {
        val token = SessionManager.fetchAuthToken(this)
        val call = RetrofitClient.instance.createProduct(token!!, product)

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AddProductActivity, "Produit créé avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@AddProductActivity, "Erreur lors de la création du produit", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@AddProductActivity, "Échec de la requête", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
