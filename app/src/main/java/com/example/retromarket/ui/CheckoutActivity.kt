package com.example.retromarket.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.retromarket.BaseActivity
import com.example.retromarket.R
import com.example.retromarket.data.api.RetrofitClient
import com.example.retromarket.data.model.CartItem
import com.example.retromarket.data.model.OrderRequest
import com.example.retromarket.data.model.OrderResponse
import com.example.retromarket.data.model.Payment
import com.example.retromarket.data.model.Shipping
import com.example.retromarket.service.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems")
        val subtotal = calculateSubtotal(cartItems)
        val tps = subtotal * 0.05
        val tvq = subtotal * 0.09975
        val total = subtotal + tps + tvq

        findViewById<TextView>(R.id.txtSubtotal).text = String.format("Sous-total: %.2f $", subtotal)
        findViewById<TextView>(R.id.txtTPS).text = String.format("TPS: %.2f %%", tps)
        findViewById<TextView>(R.id.txtTVQ).text = String.format("TVQ: %.2f %%", tvq)
        findViewById<TextView>(R.id.txtTotal).text = String.format("Total TTC: %.2f $", total)

        val btnPlaceOrder = findViewById<Button>(R.id.btnPlaceOrder)
        val btnBackToCart = findViewById<Button>(R.id.btnBackToCart)

        btnPlaceOrder.setOnClickListener {
            val address = findViewById<EditText>(R.id.etAddress).text.toString()
            val postalCode = findViewById<EditText>(R.id.etPostalCode).text.toString()
            val phone = findViewById<EditText>(R.id.etPhone).text.toString()
            val firstName = findViewById<EditText>(R.id.etFirstName).text.toString()
            val lastName = findViewById<EditText>(R.id.etLastName).text.toString()
            val cardNumber = findViewById<EditText>(R.id.etCardNumber).text.toString()

            if (validateFields(address, postalCode, phone, firstName, lastName, cardNumber)) {
                placeOrder(cartItems, address, postalCode, phone, firstName, lastName, cardNumber)
            } else {
                Toast.makeText(this, "Veuillez remplir tous les champs correctement", Toast.LENGTH_SHORT).show()
            }
        }

        btnBackToCart.setOnClickListener {
            finish()
        }
    }

    private fun calculateSubtotal(cartItems: List<CartItem>?): Double {
        return cartItems?.sumOf { it.price * it.quantity } ?: 0.0
    }

    private fun validateFields(address: String, postalCode: String, phone: String, firstName: String, lastName: String, cardNumber: String): Boolean {
        return address.isNotEmpty() && postalCode.isNotEmpty() && phone.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && cardNumber.isNotEmpty() && cardNumber.length == 16
    }

    private fun placeOrder(cartItems: List<CartItem>?, address: String, postalCode: String, phone: String, firstName: String, lastName: String, cardNumber: String) {
        val token = "Bearer ${SessionManager.fetchAuthToken(this)}"
        val userId = SessionManager.fetchUserId(this)
        val api = RetrofitClient.instance

        val orderRequest = OrderRequest(
            userId = userId,
            products = cartItems ?: emptyList(),
            shipping = Shipping(
                address = address,
                postalCode = postalCode,
                phone = phone,
                firstname = firstName,
                lastname = lastName
            ),
            payment = Payment(
                cardNumber = cardNumber
            )
        )

        api.createOrder(token, orderRequest).enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CheckoutActivity, "Commande passée avec succès", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@CheckoutActivity, "Erreur lors de la création de la commande", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(this@CheckoutActivity, "Erreur réseau : ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
