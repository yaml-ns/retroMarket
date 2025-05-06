package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.Order
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyOrderActivity : AppCompatActivity() {
    private lateinit var adapter: OrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        adapter = OrderAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        getUserOrders()
    }

    private fun getUserOrders() {
        val token = "Bearer ${SessionManager.fetchAuthToken(this)}"
        val call = RetrofitClient.instance.getUserOrders(token!!)

        call.enqueue(object : Callback<List<Order>> {
            override fun onResponse(call: Call<List<Order>>, response: Response<List<Order>>) {
                if (response.isSuccessful) {
                    val orders = response.body()
                    updateUI(orders)
                } else {
                    Toast.makeText(this@MyOrderActivity, "Erreur lors de la récupération des commandes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Order>>, t: Throwable) {
                Toast.makeText(this@MyOrderActivity, "Échec de la requête", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(orders: List<Order>?) {
        orders?.let {
            adapter.updateOrders(it)
        }
    }
}
