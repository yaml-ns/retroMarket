package com.example.retromarket.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.R
import com.example.retromarket.data.model.Order


class OrderDetailsActivity : AppCompatActivity() {
    private lateinit var adapter: OrderProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        val order = intent.getParcelableExtra<Order>("order")

        val orderIdTextView: TextView = findViewById(R.id.orderId)
        val createdAtTextView: TextView = findViewById(R.id.createdAt)
        val subtotalTextView: TextView = findViewById(R.id.subtotal)
        val taxQCTextView: TextView = findViewById(R.id.taxQC)
        val taxCATextView: TextView = findViewById(R.id.taxCA)
        val totalTextView: TextView = findViewById(R.id.total)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        orderIdTextView.text = "Commande #${order?._id}"
        createdAtTextView.text = order?.createdAt
        subtotalTextView.text = "Sous-total: ${order?.subtotal} $"
        taxQCTextView.text = "Taxe QC: ${order?.taxQC} $"
        taxCATextView.text = "Taxe CA: ${order?.taxCA} $"
        totalTextView.text = "Total: ${order?.total} $"

        adapter = OrderProductAdapter(order?.products?.toMutableList() ?: mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
