package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.CartResponse
import com.example.retromarket.data.model.Product
import com.example.retromarket.service.SessionManager
import retrofit2.Call


class ProductDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        val product = intent.getParcelableExtra<Product>("product")

        val imageView = findViewById<ImageView>(R.id.ivDetailImage)
        val descView = findViewById<TextView>(R.id.ivDescription)
        val nameView = findViewById<TextView>(R.id.ivTitle)
        val categoryView = findViewById<TextView>(R.id.tvDetailCategory)
        val priceView = findViewById<TextView>(R.id.tvDetailPrice)
        val addToCartBtn = findViewById<TextView>(R.id.addToCartBtn)
        val token = SessionManager.fetchAuthToken(this)
        addToCartBtn.setOnClickListener {
            if (token == null) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val productId = product?.id ?: return@setOnClickListener
                val call = RetrofitClient.instance.addToCart("Bearer $token", productId)

                call.enqueue(object : retrofit2.Callback<CartResponse> {
                    override fun onResponse(
                        call: Call<CartResponse>,
                        response: retrofit2.Response<CartResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ProductDetailsActivity, "Produit ajouté au panier", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ProductDetailsActivity, "Erreur: ${response.code()}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                        Toast.makeText(this@ProductDetailsActivity, "Erreur réseau : ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }



        product?.let {
            nameView.text = it.title
            descView.text = it.description
            categoryView.text = "Catégorie : ${it.category}"
            priceView.text = "${it.price} $"

            val radius = 120

            Glide.with(this@ProductDetailsActivity)
                .load(it.image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(radius)))
                .into(imageView)

        }
    }
}