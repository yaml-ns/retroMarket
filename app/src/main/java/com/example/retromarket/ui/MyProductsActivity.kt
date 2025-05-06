package com.example.retromarket.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.Product
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProductsActivity : BaseActivity() {
    private lateinit var adapter: MyProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_products)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = MyProductAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addProductButton: Button = findViewById(R.id.addProductButton)
        addProductButton.setOnClickListener {
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }

        getMyProducts()
    }

    private fun getMyProducts() {
        val token ="Bearer ${SessionManager.fetchAuthToken(this)}"
        val call = RetrofitClient.instance.getMyProducts(token!!)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    updateUI(products)
                } else {
                    Toast.makeText(this@MyProductsActivity, "Erreur lors de la récupération des produits", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@MyProductsActivity, "Échec de la requête", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(products: List<Product>?) {
        products?.let {
            adapter.updateProducts(it)
        }
    }

    fun deleteProduct(productId: Int) {
        val token = "Bearer ${SessionManager.fetchAuthToken(this)}"
        val call = RetrofitClient.instance.deleteProduct(token!!, productId)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@MyProductsActivity, "Produit supprimé avec succès", Toast.LENGTH_SHORT).show()
                    getMyProducts() // Rafraîchir la liste des produits
                } else {
                    Toast.makeText(this@MyProductsActivity, "Erreur lors de la suppression du produit", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MyProductsActivity, "Échec de la requête", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
