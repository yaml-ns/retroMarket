package com.example.retromarket

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.adapter.ProductAdapter
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.Product
import retrofit2.Call
class MainActivity : BaseActivity() {

    private lateinit var rvProducts: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectBottomItem(R.id.nav_home)
        rvProducts = findViewById(R.id.rvProducts)

        rvProducts.layoutManager = LinearLayoutManager(this)
        RetrofitClient.instance.getAllProducts().enqueue(object : retrofit2.Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: retrofit2.Response<List<Product>>) {

                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    rvProducts.adapter = ProductAdapter(this@MainActivity,products)
                    Toast.makeText(this@MainActivity,"Reussie",Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this@MainActivity,"Echouee",Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.d("SERVER_RESPONSE", "${ t.message }")
                Toast.makeText(this@MainActivity,"Probleme",Toast.LENGTH_SHORT).show()

            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // Gère les clics spécifiques à MainActivity

            else -> return super.onNavigationItemSelected(item)
        }
    }
}