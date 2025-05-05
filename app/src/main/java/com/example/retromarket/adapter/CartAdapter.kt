package com.example.retromarket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retromarket.R
import com.example.retromarket.data.model.CartItem

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items = listOf<CartItem>()

    fun setItems(newItems: List<CartItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtProductId: TextView = itemView.findViewById(R.id.txtProductId)
        val txtQuantity: TextView = itemView.findViewById(R.id.txtQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.txtProductId.text = "Produit ID: ${item.id}"
        holder.txtQuantity.text = "Quantité: ${item.quantity}"
    }

    override fun getItemCount(): Int = items.size
}
