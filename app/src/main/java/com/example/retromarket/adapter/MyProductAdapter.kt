package com.example.retromarket.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retromarket.R
import com.example.retromarket.data.model.Product

class MyProductAdapter(private val products: MutableList<Product>) : RecyclerView.Adapter<MyProductAdapter.MyProductViewHolder>() {

    class MyProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.productTitle)
        val price: TextView = itemView.findViewById(R.id.productPrice)
        val category: TextView = itemView.findViewById(R.id.txtCategory)
        val image: ImageView = itemView.findViewById(R.id.imgProduct)
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_product, parent, false)
        return MyProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        val product = products[position]
        holder.title.text = product.title
        holder.price.text = product.price.toString()
        holder.category.text = "Catégroie : ${product.category}"

        Glide.with(holder.itemView.context)
            .load(product.image)
            .into(holder.image)

        holder.deleteButton.setOnClickListener {
            (holder.itemView.context as MyProductsActivity).deleteProduct(product.id)
        }

        holder.editButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateProductActivity::class.java)
            intent.putExtra("product", product)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun updateProducts(newProducts: List<Product>) {
        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()
    }
}
