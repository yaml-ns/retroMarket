package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.adapter.CartAdapter
import com.example.retromarket.data.model.CartViewModel

class CartActivity : BaseActivity() {
    private lateinit var viewModel: CartViewModel
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        viewModel = CartViewModel(this)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
        adapter = CartAdapter { itemToDelete ->
            viewModel.deleteCartItem(itemToDelete)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val clearBtn = findViewById<Button>(R.id.btnClearCart)
        val checkoutBtn = findViewById<Button>(R.id.btnCheckout)

        viewModel.cartItems.observe(this) {
            if (it != null) {
                adapter.setItems(it)
            }
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        clearBtn.setOnClickListener { viewModel.clearCart() }
        checkoutBtn.setOnClickListener { viewModel.checkout() }


        viewModel.loadCart()
    }
}