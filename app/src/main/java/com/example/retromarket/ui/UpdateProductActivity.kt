package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.Product
import com.example.retromarket.data.model.ProductResponse
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateProductActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)

        val product = intent.getParcelableExtra<Product>("product")

        val titleEditText: EditText = findViewById(R.id.titleEditText)
        val descriptionEditText: EditText = findViewById(R.id.descriptionEditText)
        val priceEditText: EditText = findViewById(R.id.priceEditText)
        val categoryEditText: EditText = findViewById(R.id.categoryEditText)
        val imageEditText: EditText = findViewById(R.id.imageEditText)
        val updateButton: Button = findViewById(R.id.updateButton)

        titleEditText.setText(product?.title)
        descriptionEditText.setText(product?.description)
        priceEditText.setText(product?.price.toString())
        categoryEditText.setText(product?.category)
        imageEditText.setText(product?.image)

        updateButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val price = priceEditText.text.toString().toDouble()
            val category = categoryEditText.text.toString()
            val image = imageEditText.text.toString()
            val userId = SessionManager.fetchUserId(this)

            val updatedProduct = Product(product!!.id, title, description, price, category, image, userId)
            updateProduct(updatedProduct)
        }
    }

    private fun updateProduct(product: Product) {
        val token = SessionManager.fetchAuthToken(this)
        val call = RetrofitClient.instance.updateProduct(token!!, product.id, product)

        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(call: Call<ProductResponse>, response: Response<ProductResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UpdateProductActivity, "Produit mis à jour avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UpdateProductActivity, "Erreur lors de la mise à jour du produit", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(this@UpdateProductActivity, "Échec de la requête", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
