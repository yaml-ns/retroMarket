package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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
        val descView = findViewById<TextView>(R.id.ivDescription)
        val nameView = findViewById<TextView>(R.id.ivTitle)
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