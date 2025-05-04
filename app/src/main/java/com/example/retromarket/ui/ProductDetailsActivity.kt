package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.bumptech.glide.Glide
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.model.Product


class ProductDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        val product = intent.getParcelableExtra<Product>("product")

        val imageView = findViewById<ImageView>(R.id.ivDetailImage)
        val nameView = findViewById<TextView>(R.id.tvDetailName)
        val categoryView = findViewById<TextView>(R.id.tvDetailCategory)
        val priceView = findViewById<TextView>(R.id.tvDetailPrice)
        val addToCartBtn = findViewById<TextView>(R.id.addToCartBtn)
//        getSharedPreferences("retro_market", MODE_PRIVATE).edit().clear().apply()
        val prefs = getSharedPreferences("retro_market", MODE_PRIVATE)
        val token = prefs.getString("token", null)

        addToCartBtn.setOnClickListener(){
            if (token == null){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }

        product?.let {
            nameView.text = it.description
            categoryView.text = it.category
            priceView.text = "${it.price} €"

            Glide.with(this)
                .load(it.image)
                .into(imageView)
        }
    }
}